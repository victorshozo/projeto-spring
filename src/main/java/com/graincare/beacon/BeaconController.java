package com.graincare.beacon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.graincare.exceptions.BeaconHistoryNotFoundException;
import com.graincare.exceptions.SiloNotFoundException;
import com.graincare.mail.SmtpEmailSender;
import com.graincare.silos.SiloHistory;
import com.graincare.silos.SiloHistoryRepository;

@RestController
public class BeaconController {

	@Autowired
	private BeaconRepository beaconRepository;
	@Autowired
	private BeaconHistoryRepository beaconHistoryRepository;
	@Autowired
	private SiloHistoryRepository siloHistoryRepository;
	@Autowired
	private SmtpEmailSender emailSender;
	@Value("${notification.email}")
	private String emailToSendNotification;

	@RequestMapping(path = "/beacons/history", produces = "application/json", method = RequestMethod.GET)
	public List<BeaconHistory> getBeaconHistory() {
		return beaconHistoryRepository.findAll();
	}

	@RequestMapping(path = "/beacons", produces = "application/json", method = RequestMethod.GET)
	public List<Beacon> getBeacon() {
		return beaconRepository.findAll();
	}

	@RequestMapping(path = "/beacons/silo/{siloId}", produces = "application/json", method = RequestMethod.GET)
	public List<BeaconHistory> getBeaconsFor(@PathVariable Long siloId) {
		Optional<SiloHistory> optionalSiloHistory = siloHistoryRepository.findBySiloIdAndOpenFalse(siloId);
		if (!optionalSiloHistory.isPresent()) {
			throw new SiloNotFoundException();
		}

		SiloHistory siloHistory = optionalSiloHistory.get();

		return siloHistory.getBeaconsHistory();
	}

	@RequestMapping(path = "/beacons/available", produces = "application/json", method = RequestMethod.GET)
	public List<Beacon> getAvailableBeacons() {
		List<Beacon> allBeacons = beaconRepository.findAll();

		List<Beacon> unavailableBeacons = new ArrayList<>();
		siloHistoryRepository.findAllByOpenFalse().forEach(s -> {
			s.getBeaconsHistory().forEach(b -> unavailableBeacons.add(b.getBeacon()));
		});

		List<Beacon> beacons = new ArrayList<>();
		allBeacons.stream().forEach(beacon -> {
			if (!unavailableBeacons.contains(beacon)) {
				beacons.add(beacon);
			}
		});

		return beacons;
	}

	@RequestMapping(path = "/beacon/history", method = RequestMethod.POST)
	public void update(@RequestBody BeaconHistoryDTO dto) {
		List<BeaconHistory> beaconsHistories = beaconHistoryRepository.findByBeaconId(dto.getBeaconId());
		
		if(beaconsHistories.size() == 0) {
			throw new BeaconHistoryNotFoundException();
		}
		
		List<BeaconHistory> openBeaconsHistories = beaconsHistories.stream().filter(beaconHistory -> {
			return beaconHistory.getSiloHistory().getOpen() == false;
		}).collect(Collectors.toList());
			
		if (openBeaconsHistories.isEmpty()) {
			throw new InconsistentBeaconOnDatabaseException();
		}
		
		SiloHistory siloHistory = openBeaconsHistories.get(0).getSiloHistory();
		Beacon beacon = openBeaconsHistories.get(0).getBeacon();
		
		BeaconHistory beaconHistory = new BeaconHistory();
		beaconHistory.setSiloHistory(siloHistory);
		beaconHistory.setBeacon(beacon);
		beaconHistory.setHumidity(dto.getHumidity());
		beaconHistory.setDistance(dto.getDistance());
		beaconHistory.setTemperature(dto.getTemperature());
		beaconHistoryRepository.save(beaconHistory);
		
		Double grainMaxTemperature = beaconHistory.getSiloHistory().getGrao().getMaxTemperature();
		if (dto.getTemperature() > grainMaxTemperature) {
			String region = beaconHistory.getSiloHistory().getSilo().getRegion();
			String grao = beaconHistory.getSiloHistory().getGrao().getType();

			StringBuilder message = new StringBuilder();
			message.append("<span style='font-size:2em'>");
			message.append("Atenção!<br/>O silo que está com <b>" + grao + "</b> ");
			message.append("na região: <b>" + region + "</b> ");
			message.append(", está com a temperatura de " + dto.getTemperature() + " graus");
			message.append(", sendo que sua temperatura máxima é de: " + grainMaxTemperature + " graus");
			message.append("</span>");
			
			//TODO pegar esse email do login do usuario
			emailSender.to(emailToSendNotification)
					.withSubject("Atenção, silo com temperatura acima do normal")
					.withMessage(message.toString())
					.send();
		}
		
	}
}
