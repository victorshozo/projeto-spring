<<<<<<< HEAD:src/main/java/com/graincare/beacon/BeaconController.java
package com.graincare.beacon;
=======

package main;
>>>>>>> bf122af322a7dd9756e39454e762aba5d18553eb:src/main/BeaconController.java

import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeaconController {

	@RequestMapping("/beacons")
	public ArrayList<Beacon> beacon() {
		return getBeacons();
	}

	public ArrayList<Beacon> getBeacons() {
		ArrayList<Beacon> beacons = new ArrayList<>();
		for (int i = 0; i <= 13; i++) {
			int bBattery = (int) (Math.round(Math.random() * 100));
			long bTemperature = Math.round(Math.random() * 100);
			long bDistance = Math.round(Math.random() * 100);
			beacons.add(new Beacon(i, bBattery, bTemperature, 1, bDistance));
		}
		return beacons;
	}

}
