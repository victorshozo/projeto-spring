package com.graincare.silos;

import java.util.List;

import com.graincare.graos.GrainType;

public class SiloHistoryDTO {

	private Long siloId;
	private List<Long> sensorsId;
	private GrainType grainType;

	public Long getSiloId() {
		return siloId;
	}

	public void setSiloId(Long siloId) {
		this.siloId = siloId;
	}

	public List<Long> getSensorsId() {
		return sensorsId;
	}

	public void setSensorsId(List<Long> sensorsId) {
		this.sensorsId = sensorsId;
	}

	public GrainType getGrainType() {
		return grainType;
	}

	public void setGrainType(GrainType grainType) {
		this.grainType = grainType;
	}

}
