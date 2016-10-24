package com.graincare.silos;

import java.util.List;

import com.graincare.graos.GrainType;

public class SiloHistoryDTO {

	private Long siloId;
	private List<Long> beaconsId;
	private GrainType grainType;

	public Long getSiloId() {
		return siloId;
	}

	public void setSiloId(Long siloId) {
		this.siloId = siloId;
	}

	public List<Long> getBeaconsId() {
		return beaconsId;
	}

	public void setBeaconsId(List<Long> beaconsId) {
		this.beaconsId = beaconsId;
	}

	public GrainType getGrainType() {
		return grainType;
	}

	public void setGrainType(GrainType grainType) {
		this.grainType = grainType;
	}

}
