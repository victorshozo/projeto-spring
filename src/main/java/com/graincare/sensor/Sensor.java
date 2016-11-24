package com.graincare.sensor;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.graincare.farm.Farm;

@Entity
@Table(name = "sensor")
public class Sensor {

	@Id
	@GeneratedValue
	private Long id;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "farm_id", referencedColumnName = "id", nullable = false)
	private Farm farm;

	@Deprecated
	Sensor() {
	}

	public Sensor(Farm farm) {
		this.farm = farm;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Sensor))
			return false;

		Sensor other = (Sensor) obj;
		return Objects.equals(this.id, other.id);
	}
}