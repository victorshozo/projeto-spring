package com.graincare.graos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.graincare.silos.Silo;

@Entity
@Table(name = "grao")
public class Grao {
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "type")
	private String type;
	@Column(name = "max_temperature")
	private Double maxTemperature;
	@ManyToOne
	private Silo silo;

	@Deprecated
	Grao() {

	}

	public Grao(String type, Double maxTemperature, Silo silo) {
		this.type = type;
		this.maxTemperature = maxTemperature;
		this.silo = silo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(Double maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	public Silo getSilo() {
		return silo;
	}

	public void setSilo(Silo silo) {
		this.silo = silo;
	}

}
