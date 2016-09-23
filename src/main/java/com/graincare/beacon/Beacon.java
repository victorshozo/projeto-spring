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
	@Column(name = "active")
	private Boolean active;

	@Deprecated
	Beacon() {
	}

	public Beacon(Boolean active) {
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getactive() {
		return active;
	}

	public void setactive(Boolean active) {
		this.active = active;
	}

}