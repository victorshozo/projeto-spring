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

	@RequestMapping(path = "/silos/history", produces = "application/json", method = RequestMethod.GET)
	public List<SiloHistory> getSilosHistory() {
		List<SiloHistory> silos = siloHistoryRepository.findAll();
		SiloHistory siloHistory = silos.get(0);
		return silos;
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

		Double siloFullPercent = 0.0;

		for (BeaconHistory beaconHistory : siloHistory.getBeaconsHistory()) {
			if (beaconHistory.getDistance() != null) {
				siloFullPercent = (beaconHistory.getDistance() * 100.0) / siloHistory.getSilo().getSize();
			}
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
		siloHistory.setGrao(dto.getGrain());
		siloHistory.setOpen(false);
		siloHistory.setSilo(optionalSilo.get());
		siloHistoryRepository.save(siloHistory);

		for (Beacon beacon : dto.getBeacons()) {
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

		Calendar closedAt = siloHistory.getClosedAt();
		closedAt.add(Calendar.DATE, 21);
		
		PredictionSiloDTO predictionDTO = new PredictionSiloDTO();
		predictionDTO.setDate(closedAt);
		
		return predictionDTO;
	}
}
