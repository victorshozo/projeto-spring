package com.graincare.graos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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

	@Deprecated
	Grao() {

	}

	public Grao(Long id, String type, Double maxTemperature) {
		this.id = id;
		this.type = type;
		this.maxTemperature = maxTemperature;
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

}
