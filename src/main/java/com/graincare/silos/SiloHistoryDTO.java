package com.graincare.silos;

import java.util.Calendar;
import java.util.List;

import com.graincare.graos.GrainType;

public class SiloHistoryDTO {

	private Long siloId;
	private List<Long> beaconsId;
	private GrainType grainType;
	private Calendar closedAt;
	
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
	public Calendar getClosedAt() {
		return closedAt;
	}
	public void setClosedAt(Calendar closedAt) {
		this.closedAt = closedAt;
	}
}
