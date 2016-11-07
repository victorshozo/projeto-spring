package com.graincare.silos;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

public class SiloReport {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Calendar closedAt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Calendar openAt;
	private String grain;
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private Double averageTemperature;
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private Double averageHumidity;
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private Double capacityPercentUsed;
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private Double grainWeight;
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private Double grainPrice;

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

	public Double getGrainWeight() {
		return grainWeight;
	}

	public Double getGrainPrice() {
		return grainPrice;
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

	public void setGrainWeight(Double grainWeight) {
		this.grainWeight = grainWeight;
	}

	public void setGrainPrice(Double grainPrice) {
		this.grainPrice = grainPrice;
	}

}