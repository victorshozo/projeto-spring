package com.graincare.silos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SiloController {

	@Autowired
	private SiloHistoryRepository siloHistoryRepository;
	@Autowired
	private SiloRepository siloRepository;

	@RequestMapping(path = "/silos/history", produces = "application/json", method = RequestMethod.GET)
	public List<SiloHistory> getSilosHistory() {
		return siloHistoryRepository.findAll();
	}

	@RequestMapping(path = "/silos", produces = "application/json", method = RequestMethod.GET)
	public List<Silo> getSilos() {
		return siloRepository.findAll();
	}

}
