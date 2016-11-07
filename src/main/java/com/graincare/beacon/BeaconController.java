package com.graincare.beacon;

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

import com.graincare.exceptions.BeaconHistoryNotFoundException;
import com.graincare.exceptions.InconsistentBeaconOnDatabaseException;
import com.graincare.exceptions.SiloHistoryNotFoundException;
import com.graincare.mail.Email;
import com.graincare.mail.EmailSender;
import com.graincare.silos.SiloHistory;
import com.graincare.silos.SiloHistoryRepository;
import com.graincare.user.LoggedUser;

@RestController
public class BeaconController {

	@Autowired
	private BeaconRepository beaconRepository;
	@Autowired
	private BeaconHistoryRepository beaconHistoryRepository;
	@Autowired
	private SiloHistoryRepository siloHistoryRepository;
	@Autowired
	private LoggedUser loggedUser;
	@Autowired
	private EmailSender emailSender;

	@RequestMapping(path = "/beacons/history", produces = "application/json", method = RequestMethod.GET)
	public List<BeaconHistory> getBeaconHistory() {
		return beaconHistoryRepository.findByBeaconFarmUserId(loggedUser.get().getId());
	}

	@RequestMapping(path = "/beacons", produces = "application/json", method = RequestMethod.GET)
	public List<Beacon> getBeacon() {
		return beaconRepository.findByFarmUserId(loggedUser.get().getId());
	}

	@RequestMapping(path = "/beacons/silo/{siloId}", produces = "application/json", method = RequestMethod.GET)
	public List<BeaconHistory> getBeaconsFor(@PathVariable Long siloId) {
		Long userId = loggedUser.get().getId();
		Optional<SiloHistory> siloHistory = siloHistoryRepository
				.findBySiloIdAndOpenFalseAndSiloFarmUserId(siloId, userId);
		if (!siloHistory.isPresent()) {
			throw new SiloHistoryNotFoundException();
		}

		List<BeaconHistory> beaconsHistories = siloHistory.get().getBeaconsHistory()
							.stream()
							.filter(b -> b.getTemperature() != null && b.getHumidity() != null)
							.collect(Collectors.toList());
		
		List<Long> beaconsIds = new ArrayList<>();
		beaconsHistories.forEach(b -> {
			if(!beaconsIds.contains(b.getBeacon().getId())){
				beaconsIds.add(b.getBeacon().getId());
			}
		});
				
		List<BeaconHistory> result = new ArrayList<>();
		beaconsIds.forEach(beaconId -> {
			result.add(beaconHistoryRepository.findTopByBeaconFarmUserIdAndBeaconIdOrderByUpdatedAtDesc(userId, beaconId).get());
		});
		
		return result;
	}

	@RequestMapping(path = "/beacons/available", produces = "application/json", method = RequestMethod.GET)
	public List<Beacon> getAvailableBeacons() {
		Long userId = loggedUser.get().getId();
		List<Beacon> allBeacons = beaconRepository.findByFarmUserId(userId);

		List<Beacon> unavailableBeacons = new ArrayList<>();
		siloHistoryRepository.findByOpenFalseAndSiloFarmUserId(userId).forEach(s -> {
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

		if (beaconsHistories.size() == 0) {
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
			String to = siloHistory.getSilo().getFarm().getUser().getEmail();
			
			Map<String, Object> payload = new HashMap<>();
			payload.put("region", siloHistory.getSilo().getRegion());
			payload.put("grain", siloHistory.getGrao().getType());
			payload.put("farmName", siloHistory.getSilo().getFarm().getName());
			payload.put("temperature", dto.getTemperature());
			payload.put("maxTemperature", siloHistory.getGrao().getMaxTemperature());
			Email email = new Email(to, "", payload, "beacon_alert.html");
			emailSender.send(email);
		}
	}
}
