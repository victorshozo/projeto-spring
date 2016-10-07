package com.graincare.silos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.graincare.beacon.Beacon;
import com.graincare.beacon.BeaconHistory;
import com.graincare.beacon.BeaconHistoryRepository;
import com.graincare.exceptions.GrainNotFoundException;
import com.graincare.exceptions.SiloHistoryNotFoundException;
import com.graincare.exceptions.SiloNotFoundException;
import com.graincare.graos.Grao;
import com.graincare.graos.GraoRepository;

@RestController
public class SiloController {

	@Autowired
	private SiloHistoryRepository siloHistoryRepository;
	@Autowired
	private SiloRepository siloRepository;
	@Autowired
	private GraoRepository graoRepository;
	@Autowired
	private BeaconHistoryRepository beaconHistoryRepository;

	@RequestMapping(path = "/silos/history", produces = "application/json", method = RequestMethod.GET)
	public List<SiloHistory> getSilosHistory() {
		return siloHistoryRepository.findAll();
	}

	@RequestMapping(path = "/silos", produces = "application/json", method = RequestMethod.GET)
	public List<Silo> getSilos() {
		return siloRepository.findAll();
	}

	@RequestMapping(path = "/silos/abertos", produces = "application/json", method = RequestMethod.GET)
	public List<Silo> getOpenSilos() {
		List<SiloHistory> silosHistory = siloHistoryRepository.findAllByOpenTrue();
		List<Silo> silos = new ArrayList<>();

		silosHistory.stream().forEach(siloHistory -> {
			silos.add(siloHistory.getSilo());
		});

		return silos;
	}

	@RequestMapping(path = "/silo/open/{siloId}", method = RequestMethod.POST)
	public void openSilo(@PathVariable Long siloId) {
		Optional<SiloHistory> optionalSiloHistory = siloHistoryRepository.findBySiloIdAndOpenFalse(siloId);
		if (!optionalSiloHistory.isPresent()) {
			throw new SiloHistoryNotFoundException();
		}

		SiloHistory siloHistory = optionalSiloHistory.get();
		siloHistory.setOpen(true);
		siloHistory.setOpenedAt(Calendar.getInstance());
		siloHistory.getBeaconsHistory().stream().forEach(beaconHistory -> {
			beaconHistory.setDeleted(true);
		});
		siloHistoryRepository.save(siloHistory);
	}

	@RequestMapping(path = "/silo/history", method = RequestMethod.POST)
	public void createSiloHistory(@RequestBody SiloHistoryDTO dto) {
		Optional<Grao> optionalGrao = graoRepository.findByGrainType(dto.getGrain());
		Optional<Silo> optionalSilo = siloRepository.findById(dto.getSiloId());

		if (!optionalGrao.isPresent()) {
			throw new GrainNotFoundException();
		}
		if (!optionalSilo.isPresent()) {
			throw new SiloNotFoundException();
		}
		SiloHistory siloHistory = new SiloHistory();
		siloHistory.setClosedAt(Calendar.getInstance());
		siloHistory.setGrao(optionalGrao.get());
		siloHistory.setOpen(false);
		siloHistory.setSilo(optionalSilo.get());
		siloHistoryRepository.save(siloHistory);

		for (Beacon beacon : dto.getBeacons()) {
			BeaconHistory beaconHistory = new BeaconHistory();
			beaconHistory.setBeacon(beacon);
			beaconHistory.setDeleted(false);
			beaconHistory.setSiloHistory(siloHistory);
			beaconHistoryRepository.save(beaconHistory);

		}
	}

}
