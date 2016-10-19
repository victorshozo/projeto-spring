package com.graincare.graos;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GrainType {
	MILHO(36.0, "MILHO"), 
	SOJA(55.0, "SOJA");
	
	private Double maxTemperature;
	private String type;

	GrainType(Double maxTemperature, String type) {
		this.maxTemperature = maxTemperature;
		this.type = type;
	}

	public Double getMaxTemperature() {
		return this.maxTemperature;
	}
	
	public String getType() {
		return type;
	}
}
