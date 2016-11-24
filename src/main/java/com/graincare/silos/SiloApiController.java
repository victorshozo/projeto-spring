package com.graincare.silos;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graincare.exceptions.SensorNotFoundException;
import com.graincare.exceptions.SiloAlreadyInUseFoundException;
import com.graincare.exceptions.SiloHistoryNotFoundException;
import com.graincare.exceptions.SiloInUseException;
import com.graincare.exceptions.SiloNotFoundException;
import com.graincare.mail.Email;
import com.graincare.mail.EmailSender;
import com.graincare.sensor.Sensor;
import com.graincare.sensor.SensorAverage;
import com.graincare.sensor.SensorAverageService;
import com.graincare.sensor.SensorHistory;
import com.graincare.sensor.SensorHistoryRepository;
import com.graincare.sensor.SensorRepository;
import com.graincare.user.LoggedUser;
import com.graincare.user.User;

@RestController
public class SiloApiController {

	@Autowired
	private SiloHistoryRepository siloHistoryRepository;
	@Autowired
	private SiloRepository siloRepository;
	@Autowired
	private SensorHistoryRepository sensorHistoryRepository;
	@Autowired
	private SensorRepository sensorRepository;
	@Autowired
	private SiloPredictionDateCalculator siloPredictionDateCalculator;
	@Autowired
	private SensorAverageService sensorAverageService;
	@Autowired
	private LoggedUser loggedUser;
	@Autowired
	private EmailSender emailSender;
	@Autowired
	private SiloReportDTOToMapConverter siloReportDTOconverter;
	@Autowired
	private SiloReportService siloReportService;
	@Autowired
	private SiloGraphicGenerator siloGraphicGenerator;
	
	@RequestMapping(path = "/silos/history/closed", produces = "application/json", method = RequestMethod.GET)
	public List<SiloHistory> getClosedSilosHistory() {
		return siloHistoryRepository.findByOpenFalseAndSiloFarmUserId(loggedUser.get().getId());
	}

	@RequestMapping(path = "/silos/available", produces = "application/json", method = RequestMethod.GET)
	public List<Silo> getOpenSilos() {
		List<Silo> allSilos = siloRepository.findByFarmUserId(loggedUser.get().getId());

		List<Silo> closedSilos = new ArrayList<>();
		siloHistoryRepository.findByOpenFalseAndSiloFarmUserId(loggedUser.get().getId()).stream().forEach(s -> {
			closedSilos.add(s.getSilo());
		});

		List<Silo> silos = new ArrayList<>();
		allSilos.stream().forEach(silo -> {
			if (!closedSilos.contains(silo)) {
				silos.add(silo);
			}
		});

		return silos;
	}

	@RequestMapping(path = "/silo/{siloId}/capacity", method = RequestMethod.GET)
	public Double getSiloCapacity(@PathVariable Long siloId) {
		Optional<SiloHistory> optionalSiloHistory = siloHistoryRepository
				.findBySiloIdAndOpenFalseAndSiloFarmUserId(siloId, loggedUser.get().getId());
		if (!optionalSiloHistory.isPresent()) {
			throw new SiloNotFoundException();
		}
		SiloHistory siloHistory = optionalSiloHistory.get();

		List<SensorHistory> sensorsHistories = siloHistory.getSensorsHistory().stream().filter(b -> {
			return b.getHumidity() == null && b.getTemperature() == null;
		}).collect(Collectors.toList());

		SensorHistory sensorHistory = new SensorHistory();
		sensorHistory.setUpdatedAt(null);
		for (SensorHistory b : sensorsHistories) {
			if (b.getUpdatedAt().after(sensorHistory.getUpdatedAt()) || sensorHistory.getUpdatedAt() == null) {
				sensorHistory = b;
			}
		}

		Double siloFullPercent = 0.0;
		if (sensorHistory.getDistance() != null) {
			siloFullPercent = (sensorHistory.getDistance() * 100.0) / siloHistory.getSilo().getSize();
		}

		return siloFullPercent;
	}

	@RequestMapping(path = "/silos", produces = "application/json", method = RequestMethod.GET)
	public List<Silo> getSilos() {
		return siloRepository.findByFarmUserId(loggedUser.get().getId());
	}

	@RequestMapping(path = "/silo/{siloId}/open", method = RequestMethod.POST)
	public void openSilo(@PathVariable Long siloId) {
		Optional<SiloHistory> optionalSiloHistory = siloHistoryRepository
				.findBySiloIdAndOpenFalseAndSiloFarmUserId(siloId, loggedUser.get().getId());
		if (!optionalSiloHistory.isPresent()) {
			throw new SiloHistoryNotFoundException();
		}

		SiloHistory siloHistory = optionalSiloHistory.get();
		siloHistory.setOpen(true);
		siloHistory.setOpenedAt(Calendar.getInstance());
		siloHistoryRepository.save(siloHistory);
	}

	@RequestMapping(path = "/silo/history", method = RequestMethod.POST)
	public void createSiloHistory(@RequestBody SiloHistoryDTO dto) {
		Long userId = loggedUser.get().getId();

		Optional<Silo> optionalSilo = siloRepository.findByIdAndFarmUserId(dto.getSiloId(), userId);
		if (!optionalSilo.isPresent()) {
			throw new SiloNotFoundException();
		}

		if (siloHistoryRepository.findBySiloIdAndOpenFalseAndSiloFarmUserId(dto.getSiloId(), userId).isPresent()) {
			throw new SiloAlreadyInUseFoundException();
		}

		List<Sensor> sensors = sensorRepository.findByFarmUserIdAndIdIn(userId, dto.getSensorsId());
		if (sensors.size() != dto.getSensorsId().size()) {
			throw new SensorNotFoundException();
		}

		SiloHistory siloHistory = new SiloHistory();
		siloHistory.setClosedAt(Calendar.getInstance());
		siloHistory.setGrao(dto.getGrainType());
		siloHistory.setOpen(false);
		siloHistory.setSilo(optionalSilo.get());
		siloHistoryRepository.save(siloHistory);
	}

	@RequestMapping(path = "/silo/{siloId}/prediction", method = RequestMethod.GET)
	public PredictionSiloDTO getPrediction(@PathVariable Long siloId) {
		Optional<SiloHistory> optionalSiloHistory = siloHistoryRepository
				.findBySiloIdAndOpenFalseAndSiloFarmUserId(siloId, loggedUser.get().getId());
		if (!optionalSiloHistory.isPresent()) {
			throw new SiloHistoryNotFoundException();
		}
		SiloHistory siloHistory = optionalSiloHistory.get();

		List<SensorAverage> averages = new ArrayList<>();
		List<Object[]> results = sensorHistoryRepository.getListOfAverageTemperatureAndHumidityFor(siloHistory.getId());
		results.forEach(result -> {
			SensorAverage average = sensorAverageService.getSensorAverageFor(result);
			averages.add(average);
		});

		Calendar predictionDate = siloPredictionDateCalculator.calculate(siloHistory, averages);
		return new PredictionSiloDTO(predictionDate);
	}
	
	@RequestMapping(path = "/silos/{siloId}/report", method = RequestMethod.GET)
	public SiloReportDTO getReport(@PathVariable Long siloId,
			@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate) {
		SiloReportDTO siloReportDTO = siloReportService.generateReport(siloId, startDate, endDate);
		
		return siloReportDTO;
	}
	
	@RequestMapping(path = "/silos/{siloId}/report/email", method = RequestMethod.POST)
	public void senReportToEmail(@PathVariable Long siloId,
			@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate) {
		User user = loggedUser.get();
		
		SiloReportDTO siloReportDTO = siloReportService.generateReport(siloId, startDate, endDate);
		if(siloReportDTO.getData().size() > 0){
			Map<String, Object> payload = siloReportDTOconverter.convert(siloReportDTO);
			
			Email email = new Email(user.getEmail(), "Relatório de silo", payload , "report.html");
			emailSender.send(email);
		}
	}
	
	@RequestMapping(path = "/silos/{siloId}/graphic", method = RequestMethod.GET)
	public SiloGraphicDTO getGraph(@PathVariable Long siloId,
			@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate) {
		
		Optional<Silo> silo = siloRepository.findByIdAndFarmUserId(siloId, loggedUser.get().getId());
		if(!silo.isPresent()) {
			throw new SiloNotFoundException();
		}
		
		List<Object[]> results = siloHistoryRepository.generateGraphicFor(siloId, startDate, endDate);
		SiloGraphicDTO dto = siloGraphicGenerator.generateGraphicFor(results, startDate, endDate);
		return dto;
	}
	
	@RequestMapping(path = "/silos/{siloId}/delete", method = POST)
	public void deleteSensor(@PathVariable("siloId") Long siloId){
		List<SiloHistory> silo = siloHistoryRepository.findBySiloFarmUserId(siloId);
		if(silo.size() >= 1){
			throw new SiloInUseException();
		}
		siloRepository.delete(siloId);
	}
}
