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
				new Silo(1, 1, 10000.500, "2016/09/10", "2016/10/10", "Inferno"),
				new Silo(2, 2, 10000.500, "2016/09/10", "2016/10/10", "Hell"),
				new Silo(3, 3, 10000.500, "2016/09/10", "2016/10/10", "ASD")
			);
	}

}
