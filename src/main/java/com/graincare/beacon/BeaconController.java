package com.graincare.beacon;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BeaconController {

	@RequestMapping("/beacons")
	public List<Beacon> beacon() {
		return Arrays.asList(
				new Beacon(1,1,22,12,30),
				new Beacon(2,2,36,18,33),
				new Beacon(3,1,39,20,2),
				new Beacon(4,2,12,27,21)
			);
	}
}
