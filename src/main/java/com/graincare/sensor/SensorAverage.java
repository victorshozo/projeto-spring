package com.graincare.sensor;

import java.util.Calendar;

public class SensorAverage {
	private Calendar date;
	private int quantity;
	private Double averageTemperature;
	private Double averageHumidity;

	public SensorAverage(Calendar date, int quantity, Double averageTemperature, Double averageHumidity) {
		this.date = date;
		this.quantity = quantity;
		this.averageTemperature = averageTemperature;
		this.averageHumidity = averageHumidity;
	}

	public Calendar getDate() {
		return date;
	}

	public int getQuantity() {
		return quantity;
	}

	public Double getAverageTemperature() {
		return averageTemperature;
	}

	public Double getAverageHumidity() {
		return averageHumidity;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setAverageTemperature(Double averageTemperature) {
		this.averageTemperature = averageTemperature;
	}

	public void setAverageHumidity(Double averageHumidity) {
		this.averageHumidity = averageHumidity;
	}

}
