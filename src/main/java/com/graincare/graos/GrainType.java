package com.graincare.graos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GrainType {
	MILHO(20.0, 13.0, "MILHO"),
	SOJA(20.0, 13.5, "SOJA"),
	SORGO(20.0, 15.0, "SORGO");

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
