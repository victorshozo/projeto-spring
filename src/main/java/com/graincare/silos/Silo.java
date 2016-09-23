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
	@Column(name = "open_status")
	private Boolean openStatus;
	@Column(name = "capacity")
	private Double capacity;
	@Column(name = "region")
	private String region;

	public Silo(Long id, Boolean openStatus, Double capacity, String region) {
		this.id = id;
		this.openStatus = openStatus;
		this.capacity = capacity;
		this.region = region;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getOpenStatus() {
		return openStatus;
	}

	public void setOpenStatus(Boolean openStatus) {
		this.openStatus = openStatus;
	}

	public Double getCapacity() {
		return capacity;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

}
