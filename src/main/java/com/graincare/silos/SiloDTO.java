package com.graincare.silos;

public class SiloDTO {
	private Long farmId;
	private String region;
	private Double size;
	private Double capacity;

	public Long getFarmId() {
		return farmId;
	}

	public String getRegion() {
		return region;
	}

	public Double getSize() {
		return size;
	}

	public Double getCapacity() {
		return capacity;
	}

	public void setFarmId(Long farmId) {
		this.farmId = farmId;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setSize(Double size) {
		this.size = size;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}

}
