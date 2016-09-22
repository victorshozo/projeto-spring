package com.graincare.beacon;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.graincare.silos.Silo;

@Entity
@Table(name = "beacon")
public class Beacon {
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "temperature")
	private long temperature;
	@Column(name = "distance")
	private long distance;
	@ManyToOne(fetch = FetchType.EAGER)
	private Silo silo;
	@Column(name = "humidity")
	private long humidity;

	@Deprecated
	Beacon() {
	}

	public Beacon(long temperature, long distance, Silo silo, long humidity) {
		this.temperature = temperature;
		this.distance = distance;
		this.silo = silo;
		this.humidity = humidity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getTemperature() {
		return temperature;
	}

	public void setTemperature(long temperature) {
		this.temperature = temperature;
	}

	public long getDistance() {
		return distance;
	}

	public void setDistance(long distance) {
		this.distance = distance;
	}

	public Silo getSilo() {
		return silo;
	}

	public void setSilo(Silo silo) {
		this.silo = silo;
	}

	public long getHumidity() {
		return humidity;
	}

	public void setHumidity(long humidity) {
		this.humidity = humidity;
	}

}