package com.graincare.beacon;

import java.util.Calendar;

public class BeaconAvarage {
	private Calendar date;
	private int quantity;
	private Double avarageTemperature;
	private Double avarageHumidity;

	public BeaconAvarage(Calendar date, int quantity, Double avarageTemperature, Double avarageHumidity) {
		this.date = date;
		this.quantity = quantity;
		this.avarageTemperature = avarageTemperature;
		this.avarageHumidity = avarageHumidity;
	}

	public Calendar getDate() {
		return date;
	}

	public int getQuantity() {
		return quantity;
	}

	public Double getAvarageTemperature() {
		return avarageTemperature;
	}

	public Double getAvarageHumidity() {
		return avarageHumidity;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setAvarageTemperature(Double avarageTemperature) {
		this.avarageTemperature = avarageTemperature;
	}

	public void setAvarageHumidity(Double avarageHumidity) {
		this.avarageHumidity = avarageHumidity;
	}

}
