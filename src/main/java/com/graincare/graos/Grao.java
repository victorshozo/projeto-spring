package com.graincare.graos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "grao")
public class Grao {
	@Id
	@GeneratedValue
	private Long id;
	@Enumerated(EnumType.STRING)
	@Column(name = "grain_type")
	private GrainType grainType;
	@Column(name = "max_temperature")
	private Double maxTemperature;

	@Deprecated
	Grao() {
	}

	public Long getId() {
		return id;
	}

	public GrainType getGrainType() {
		return grainType;
	}

	public Double getMaxTemperature() {
		return maxTemperature;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setGrainType(GrainType grainType) {
		this.grainType = grainType;
	}

	public void setMaxTemperature(Double maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

}
