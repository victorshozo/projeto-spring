package com.graincare.graos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GrainType {
	MILHO(44.0, 13.0, "MILHO"),
	SOJA(30.0, 13.5, "SOJA"),
	SORGO(30.0, 15.0, "SORGO");

	private Double maxTemperature;
	private Double maxHumidity;
	private String type;

	GrainType(Double maxTemperature, Double maxHumidity, String type) {
		this.maxTemperature = maxTemperature;
		this.maxHumidity = maxHumidity;
		this.type = type;
	}

	public Double getMaxTemperature() {
		return this.maxTemperature;
	}

	public String getType() {
		return type;
	}
	
	@JsonIgnore
	public Double getMaxHumidity() {
		return this.maxHumidity;
	}
	
	@JsonIgnore
	public int getDaysPerDegree() {
		return getMaxTemperature().intValue() / 21;
	}
}
