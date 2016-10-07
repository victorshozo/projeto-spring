package com.graincare.silos;

import java.util.List;

import com.graincare.beacon.Beacon;
import com.graincare.graos.GrainType;

public class SiloHistoryDTO {

	private Long siloId;
	private List<Beacon> beacons;
	// TODO alterar o nome desse atributo
	private GrainType grain;

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
		return grain;
	}

	public void setGrain(GrainType grain) {
		this.grain = grain;
	}

}
