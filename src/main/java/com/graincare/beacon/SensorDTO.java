package com.graincare.beacon;

public class SensorDTO {
	private Long farmId;
	private Integer quantity;

	public Long getFarmId() {
		return farmId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setFarmId(Long farmId) {
		this.farmId = farmId;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}