package com.graincare.beacon;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.graincare.silos.Silo;
import com.graincare.silos.SiloHistory;

@RestController
public class BeaconController {

	@Autowired
	private BeaconRepository beaconRepository;

	@Autowired
	private BeaconHistoryRepository beaconHistoryRepository;

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
}
