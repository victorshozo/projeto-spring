package com.graincare.graos;

public enum GrainType {
	MILHO(36),
	SOJA(55)
	;
private Double maxTemperature;

GrainType(Double maxTemperature){
	this.maxTemperature = maxTemperature;
}

public Double getMaxTemperature(){
	return this.maxTemperature;
}
}
