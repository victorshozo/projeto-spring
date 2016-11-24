package com.graincare.sensor;

public class SensorHistoryDTO {
	private Long sensorId;
	private Double temperature;
	private Double humidity;
	private Double distance;

	public Long getSensorId() {
		return sensorId;
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

	public void setSensorId(Long sensorId) {
		this.sensorId = sensorId;
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
