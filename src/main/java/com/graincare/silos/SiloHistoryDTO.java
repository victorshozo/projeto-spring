package com.graincare.silos;

import java.util.List;

import com.graincare.beacon.Beacon;
import com.graincare.graos.GrainType;

public class SiloHistoryDTO {

	private Long siloId;
	private List<Beacon> beacons;
	private GrainType grainType;

	public Long getSiloId() {
		return siloId;
	}

	public void setSiloId(Long siloId) {
		this.siloId = siloId;
	}

	public List<Beacon> getBeacons() {
		return beacons;
	}

	public void setBeacons(List<Beacon> beacons) {
		this.beacons = beacons;
	}

	public GrainType getGrain() {
		return grainType;
	}

	public void setGrain(GrainType grain) {
		this.grainType = grain;
	}

}
