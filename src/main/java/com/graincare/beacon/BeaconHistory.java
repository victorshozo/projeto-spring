package com.graincare.beacon;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.graincare.silos.SiloHistory;

@Entity
@Table(name = "beacon_history")
public class BeaconHistory {
	@Id
	@GeneratedValue
	private Long id;
	@OneToOne
	@NotNull
	private Beacon beacon;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "silo_history_id")
	@JsonBackReference
	private SiloHistory siloHistory;
	@Column(name = "temperature")
	private Double temperature;
	@Column(name = "distance")
	private Double distance;
	@Column(name = "humidity")
	private Double humidity;

	@Deprecated
	BeaconHistory() {
	}

	public Long getId() {
		return id;
	}

	public Beacon getBeacon() {
		return beacon;
	}

	public SiloHistory getSiloHistory() {
		return siloHistory;
	}

	public Double getTemperature() {
		return temperature;
	}

	public Double getDistance() {
		return distance;
	}

	public Double getHumidity() {
		return humidity;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setBeacon(Beacon beacon) {
		this.beacon = beacon;
	}

	public void setSiloHistory(SiloHistory siloHistory) {
		this.siloHistory = siloHistory;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

}