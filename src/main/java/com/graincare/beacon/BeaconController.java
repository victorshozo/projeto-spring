package com.graincare.beacon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.graincare.exceptions.BeaconHistoryNotFoundException;
import com.graincare.exceptions.SiloNotFoundException;
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
		Optional<BeaconHistory> optionalBeaconHistory = beaconHistoryRepository.findByBeaconId(dto.getBeaconId());
		if (!optionalBeaconHistory.isPresent()) {
			throw new BeaconHistoryNotFoundException();
		}

		BeaconHistory beaconHistory = optionalBeaconHistory.get();
		beaconHistory.setHumidity(dto.getHumidity());
		beaconHistory.setDistance(dto.getDistance());
		beaconHistory.setTemperature(dto.getTemperature());
		beaconHistoryRepository.save(beaconHistory);
	}
}
