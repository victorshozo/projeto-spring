package com.graincare.sensor;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.graincare.exceptions.InconsistentSensorOnDatabaseException;
import com.graincare.exceptions.SensorHistoryNotFoundException;
import com.graincare.exceptions.SensorInUseException;
import com.graincare.exceptions.SiloHistoryNotFoundException;
import com.graincare.mail.Email;
import com.graincare.mail.EmailSender;
import com.graincare.silos.SiloHistory;
import com.graincare.silos.SiloHistoryRepository;
import com.graincare.user.LoggedUser;

@RestController
public class SensorApiController {

	@Autowired
	private SensorRepository sensorRepository;
	@Autowired
	private SensorHistoryRepository sensorHistoryRepository;
	@Autowired
	private SiloHistoryRepository siloHistoryRepository;
	@Autowired
	private LoggedUser loggedUser;
	@Autowired
	private EmailSender emailSender;

	@RequestMapping(path = "/sensors/silo/{siloId}", produces = "application/json", method = RequestMethod.GET)
	public List<SensorHistory> getSensorsFor(@PathVariable Long siloId) {
		Long userId = loggedUser.get().getId();
		Optional<SiloHistory> siloHistory = siloHistoryRepository
				.findBySiloIdAndOpenFalseAndSiloFarmUserId(siloId, userId);
		if (!siloHistory.isPresent()) {
			throw new SiloHistoryNotFoundException();
		}

		List<SensorHistory> sensorsHistories = siloHistory.get().getSensorsHistory()
							.stream()
							.filter(b -> b.getTemperature() != null && b.getHumidity() != null)
							.collect(Collectors.toList());
		
		List<Long> sensorsIds = new ArrayList<>();
		sensorsHistories.forEach(b -> {
			if(!sensorsIds.contains(b.getSensor().getId())){
				sensorsIds.add(b.getSensor().getId());
			}
		});
				
		List<SensorHistory> result = new ArrayList<>();
		sensorsIds.forEach(sensorId -> {
			result.add(sensorHistoryRepository.findTopBySensorFarmUserIdAndSensorIdOrderByUpdatedAtDesc(userId, sensorId).get());
		});
		
		return result;
	}

	@RequestMapping(path = "/sensors/available", produces = "application/json", method = RequestMethod.GET)
	public List<Sensor> getAvailableSensors() {
		Long userId = loggedUser.get().getId();
		List<Sensor> allSensors = sensorRepository.findByFarmUserId(userId);

		List<Sensor> unavailableSensors = new ArrayList<>();
		siloHistoryRepository.findByOpenFalseAndSiloFarmUserId(userId).forEach(s -> {
			s.getSensorsHistory().forEach(b -> unavailableSensors.add(b.getSensor()));
		});

		List<Sensor> sensors = new ArrayList<>();
		allSensors.stream().forEach(sensor -> {
			if (!unavailableSensors.contains(sensor)) {
				sensors.add(sensor);
			}
		});

		return sensors;
	}

	@RequestMapping(path = "/sensor/history", method = RequestMethod.POST)
	public void update(@RequestBody SensorHistoryDTO dto) {
		List<SensorHistory> sensorHistories = sensorHistoryRepository.findBySensorId(dto.getSensorId());

		if (sensorHistories.size() == 0) {
			throw new SensorHistoryNotFoundException();
		}

		List<SensorHistory> openSensorHistories = sensorHistories.stream().filter(sensorHistory -> {
			return sensorHistory.getSiloHistory().getOpen() == false;
		}).collect(Collectors.toList());

		if (openSensorHistories.isEmpty()) {
			throw new InconsistentSensorOnDatabaseException();
		}

		SiloHistory siloHistory = openSensorHistories.get(0).getSiloHistory();
		Sensor sensor = openSensorHistories.get(0).getSensor();

		SensorHistory sensorHistory = new SensorHistory();
		sensorHistory.setSiloHistory(siloHistory);
		sensorHistory.setSensor(sensor);
		sensorHistory.setHumidity(dto.getHumidity());
		sensorHistory.setDistance(dto.getDistance());
		sensorHistory.setTemperature(dto.getTemperature());
		sensorHistoryRepository.save(sensorHistory);

		Double grainMaxTemperature = sensorHistory.getSiloHistory().getGrao().getMaxTemperature();
		if (dto.getTemperature() > grainMaxTemperature) {
			String to = siloHistory.getSilo().getFarm().getUser().getEmail();
			
			Map<String, Object> payload = new HashMap<>();
			payload.put("region", siloHistory.getSilo().getRegion());
			payload.put("grain", siloHistory.getGrao().getType());
			payload.put("farmName", siloHistory.getSilo().getFarm().getName());
			payload.put("temperature", dto.getTemperature());
			payload.put("maxTemperature", siloHistory.getGrao().getMaxTemperature());
			Email email = new Email(to, "ATENÇÃO, SILO COM TEMPERATURA ACIMA DO LIMITE", payload, "sensor_alert.html");
			emailSender.send(email);
		}
	}
	
	@RequestMapping(path = "/sensors/{sensorId}/delete", method = POST)
	public void deleteSensor(@PathVariable("sensorId") Long sensorId){
		List<SensorHistory> sensors = sensorHistoryRepository.findBySensorId(sensorId);
		if(sensors.size() >= 1){
			throw new SensorInUseException();
		}
		sensorRepository.delete(sensorId);
	}
	
}
