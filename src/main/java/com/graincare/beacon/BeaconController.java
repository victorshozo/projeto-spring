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

import com.graincare.exceptions.SiloHistoryNotFoundException;
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

	@RequestMapping(path = "/beacon", produces = "application/json", method = RequestMethod.GET)
	public List<Beacon> getBeacon() {
		return beaconRepository.findAll();
	}

	@RequestMapping(path = "/beacon/history", produces = "application/json", method = RequestMethod.GET)
	public List<BeaconHistory> getBeaconHistory() {
		return beaconHistoryRepository.findAll();
	}

	@RequestMapping(path = "/beacon/disponivel", produces = "application/json", method = RequestMethod.GET)
	public List<Beacon> getAvailableBeacons() {
		return beaconRepository.findAllByAvailableTrue();

	}

	@RequestMapping(path = "/beacons/silo-history/{siloHistoryId}", produces = "application/json", method = RequestMethod.GET)
	public List<Beacon> getBeaconsFor(@PathVariable Long siloHistoryId) {
		Optional<SiloHistory> optionalSiloHistory = siloHistoryRepository.findById(siloHistoryId);
		if (!optionalSiloHistory.isPresent()) {
			throw new SiloHistoryNotFoundException();
		}

		SiloHistory siloHistory = optionalSiloHistory.get();
		List<BeaconHistory> beaconsHistory = siloHistory.getBeaconsHistory();

		List<Beacon> beacons = new ArrayList<>();
		for (BeaconHistory beaconHistory : beaconsHistory) {
			beacons.add(beaconHistory.getBeacon());
		}
		return beacons;

	}

	@RequestMapping(path = "/beacon", method = RequestMethod.POST)
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
