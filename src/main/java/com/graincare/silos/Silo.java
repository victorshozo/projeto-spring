package com.graincare.silos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "silo")
public class Silo {
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "capacity")
	private Double capacity;
	@Column(name = "region")
	private String region;

	@Deprecated
	Silo() {
	}

	public Long getId() {
		return id;
	}

	public Double getCapacity() {
		return capacity;
	}

	public String getRegion() {
		return region;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}

	public void setRegion(String region) {
		this.region = region;
	}

}
