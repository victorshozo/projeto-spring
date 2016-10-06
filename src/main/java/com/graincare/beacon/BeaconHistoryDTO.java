package com.graincare.beacon;

public class BeaconHistoryDTO {
	private Long beaconId;
	private Double temperature;
	private Double humidity;
	private Double distance;

	public Long getBeaconId() {
		return beaconId;
	}

	public Double getTemperature() {
		return temperature;
	}

	public Double getHumidity() {
		return humidity;
	}

	public Double getDistance() {
		return distance;
	}

	public void setBeaconId(Long beaconId) {
		this.beaconId = beaconId;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

}
