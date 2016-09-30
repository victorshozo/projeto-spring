package com.graincare.graos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GraoController {

	@Autowired
	private GraoRepository graoRepository;

	@RequestMapping(path = "/graos", produces = "application/json", method = RequestMethod.GET)
	public List<Grao> getGraos() {
		return graoRepository.findAll();
	}

	
}