package com.graincare.silos;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SiloReport {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Calendar closedAt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Calendar openAt;
	private String grain;
	private Double averageTemperature;
	private Double averageHumidity;
	private Double capacityPercentUsed;

	public Calendar getClosedAt() {
		return closedAt;
	}

	public Calendar getOpenAt() {
		return openAt;
	}

	public String getGrain() {
		return grain;
	}

	public Double getAverageTemperature() {
		return averageTemperature;
	}

	public Double getAverageHumidity() {
		return averageHumidity;
	}

	public Double getCapacityPercentUsed() {
		return capacityPercentUsed;
	}

	public void setClosedAt(Calendar closedAt) {
		this.closedAt = closedAt;
	}

	public void setOpenAt(Calendar openAt) {
		this.openAt = openAt;
	}

	public void setGrain(String grain) {
		this.grain = grain;
	}

	public void setAverageTemperature(Double averageTemperature) {
		this.averageTemperature = averageTemperature;
	}

	public void setAverageHumidity(Double averageHumidity) {
		this.averageHumidity = averageHumidity;
	}

	public void setCapacityPercentUsed(Double capacityPercentUsed) {
		this.capacityPercentUsed = capacityPercentUsed;
	}

}
