package com.graincare.beacon;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "beacon")
public class Beacon {
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "available")
	private Boolean available;

	@Deprecated
	Beacon() {
	}

	public Beacon(Boolean available) {
		this.available = available;
	}

	public Long getId() {
		return id;
	}

	public Boolean isAvailable() {
		return available;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

}