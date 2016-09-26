package com.graincare.beacon;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BeaconController {

	@RequestMapping("/beacons")
	public List<Beacon> beacon() {
		return Arrays.asList();
	}
}
