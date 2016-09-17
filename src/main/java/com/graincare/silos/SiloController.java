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
				new Silo(1, 1, 1000.10, "10/03/2016", "20/06/2016", "Noroeste"),
				new Silo(2, 1, 10000.0, "10/02/2016", "20/05/2016", "Agreste"),
				new Silo(3, 3, 100000.0, "10/01/2016", "20/04/2016", "Inferno"),
				new Silo(4, 2, 15000.0, "10/04/2016", "20/07/2016", "Satan")
			);
	}

}
