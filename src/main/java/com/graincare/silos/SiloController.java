package com.graincare.silos;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SiloController {

	@RequestMapping("/silos")
	public List<Silo> silo() {
		return Arrays.asList(
			);
	}

}
