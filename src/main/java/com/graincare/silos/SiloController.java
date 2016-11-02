package com.graincare.silos;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.graincare.beacon.Beacon;
import com.graincare.beacon.BeaconAverage;
import com.graincare.beacon.BeaconHistory;
import com.graincare.beacon.BeaconHistoryRepository;
import com.graincare.exceptions.SiloHistoryNotFoundException;
import com.graincare.exceptions.SiloNotFoundException;

@RestController
public class SiloController {

	@Autowired
	private SiloHistoryRepository siloHistoryRepository;
	@Autowired
	private SiloRepository siloRepository;
	@Autowired
	private BeaconHistoryRepository beaconHistoryRepository;
	@Autowired
	private SiloPredictionDateCalculator siloPredictionDateCalculator;

	@RequestMapping(path = "/silos/history", produces = "application/json", method = RequestMethod.GET)
	public List<SiloHistory> getSilosHistory() {
		return siloHistoryRepository.findAll();
	}

	@RequestMapping(path = "/silos/history/closed", produces = "application/json", method = RequestMethod.GET)
	public List<SiloHistory> getClosedSilosHistory() {
		return siloHistoryRepository.findAllByOpenFalse();
	}

	@RequestMapping(path = "/silos/available", produces = "application/json", method = RequestMethod.GET)
	public List<Silo> getOpenSilos() {
		List<Silo> allSilos = siloRepository.findAll();

		List<Silo> closedSilos = new ArrayList<>();
		siloHistoryRepository.findAllByOpenFalse().stream().forEach(s -> {
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
		Optional<SiloHistory> optionalSiloHistory = siloHistoryRepository.findBySiloIdAndOpenFalse(siloId);
		if (!optionalSiloHistory.isPresent()) {
			throw new SiloNotFoundException();
		}
		SiloHistory siloHistory = optionalSiloHistory.get();

		List<BeaconHistory> beaconsHistories = siloHistory.getBeaconsHistory().stream().filter(b -> {
			return b.getHumidity() == null && b.getTemperature() == null;
		}).collect(Collectors.toList());
				
		BeaconHistory beaconHistory = new BeaconHistory();
		beaconHistory.setUpdatedAt(null);
		for (BeaconHistory b : beaconsHistories) {
			if(b.getUpdatedAt().after(beaconHistory.getUpdatedAt()) || beaconHistory.getUpdatedAt() == null) {
				beaconHistory = b;
			}			
		}
		
		Double siloFullPercent = 0.0;
		if (beaconHistory.getDistance() != null) {
			siloFullPercent = (beaconHistory.getDistance() * 100.0) / siloHistory.getSilo().getSize();
		}

		return siloFullPercent;
	}

	@RequestMapping(path = "/silos", produces = "application/json", method = RequestMethod.GET)
	public List<Silo> getSilos() {
		return siloRepository.findAll();
	}

	@RequestMapping(path = "/silo/{siloId}/open", method = RequestMethod.POST)
	public void openSilo(@PathVariable Long siloId) {
		Optional<SiloHistory> optionalSiloHistory = siloHistoryRepository.findBySiloIdAndOpenFalse(siloId);
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
		Optional<Silo> optionalSilo = siloRepository.findById(dto.getSiloId());
		if (!optionalSilo.isPresent()) {
			throw new SiloNotFoundException();
		}
		SiloHistory siloHistory = new SiloHistory();
		siloHistory.setClosedAt(Calendar.getInstance());
		siloHistory.setGrao(dto.getGrainType());
		siloHistory.setOpen(false);
		siloHistory.setSilo(optionalSilo.get());
		siloHistoryRepository.save(siloHistory);

		for (Long beaconId : dto.getBeaconsId()) {
			Beacon beacon = new Beacon(beaconId);
			
			BeaconHistory beaconHistory = new BeaconHistory();
			beaconHistory.setBeacon(beacon);
			beaconHistory.setSiloHistory(siloHistory);
			beaconHistoryRepository.save(beaconHistory);
		}
	}

	@RequestMapping(path = "/silo/{siloId}/prediction", method = RequestMethod.GET)	
	public PredictionSiloDTO getPrediction(@PathVariable Long siloId) {
		Optional<SiloHistory> optionalSiloHistory = siloHistoryRepository.findBySiloIdAndOpenFalse(siloId);
		if (!optionalSiloHistory.isPresent()) {
			throw new SiloHistoryNotFoundException();
		}
		SiloHistory siloHistory = optionalSiloHistory.get();
		
		List<BeaconAverage> averages = new ArrayList<>();
		List<Object[]> results = beaconHistoryRepository.getListOfAverageTemperatureAndHumidityFor(siloHistory.getId());
		results.forEach(result -> {
			BeaconAverage average = getBeaconAverage(result);
			averages.add(average);
		});
		
		Calendar predictionDate = siloPredictionDateCalculator.calculate(siloHistory, averages);
		return new PredictionSiloDTO(predictionDate);
	}
	
	@RequestMapping(path = "/silo/{siloId}/report", method = RequestMethod.GET)
	public SiloReportDTO getReport(@PathVariable Long siloId){
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MONTH, -3);
		
		List<SiloReport> siloReports = new ArrayList<>();
		
		siloHistoryRepository.generateReportFor(siloId).forEach(result -> {
			SiloReport siloReport = new SiloReport();
			
			Calendar closedAt = Calendar.getInstance();
			date.setTimeInMillis(((Date) result[0]).getTime());
			siloReport.setClosedAt(closedAt);
			
			Calendar openAt = Calendar.getInstance();
			date.setTimeInMillis(((Date) result[1]).getTime());
			siloReport.setOpenAt(openAt);
			
			siloReport.setGrain((String) result[2]);
			siloReport.setAverageTemperature((Double) result[3]);
			siloReport.setAverageHumidity((Double) result[4]);
			siloReport.setCapacityPercentUsed((Double) result[5]);
			
			siloReports.add(siloReport);
		});
		
		Double siloCapacityUse = 0.0;
		for (SiloReport siloReport : siloReports) {
			siloCapacityUse += siloReport.getCapacityPercentUsed();
		}
		siloCapacityUse = siloCapacityUse / siloReports.size();
		
		SiloReportDTO siloReportDTO = new SiloReportDTO();
		siloReportDTO.setSiloId(siloId);
		siloReportDTO.setSiloCapacityUse(siloCapacityUse);
		siloReportDTO.setData(siloReports);
		
		return siloReportDTO;
	}
	
	private BeaconAverage getBeaconAverage(Object[] result) {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(((Date) result[0]).getTime());
		
		int quantityOfTemperatures = ((BigDecimal) result[1]).intValue();
		Double averageTemperature = (Double) result[2];
		Double averageHumidity = (Double) result[3];
		
		BeaconAverage average = new BeaconAverage(date, quantityOfTemperatures, averageTemperature, averageHumidity);
		return average;
	}
}
