package com.graincare.graos;

public enum GrainType {
	MILHO(36.0),
	SOJA(55.0)
	;
private Double maxTemperature;

GrainType(Double maxTemperature){
	this.maxTemperature = maxTemperature;
}

public Double getMaxTemperature(){
	return this.maxTemperature;
}
}
