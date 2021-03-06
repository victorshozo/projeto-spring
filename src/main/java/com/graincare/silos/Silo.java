package com.graincare.silos;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.graincare.farm.Farm;

@Entity
@Table(name = "silo")
@Where(clause = "deleted = 0")
public class Silo {
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "region")
	private String region;
	@Column(name = "size")
	private Double size = 30d;
	@Column(name = "capacity")
	private Double capacity;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "farm_id", referencedColumnName = "id", nullable = false)
	private Farm farm;
	@Column(name = "deleted")
	private boolean deleted;

	@Deprecated
	Silo() {
	}
	
	public Silo(Farm farm, String region, Double size, Double  capacity){
		this.farm = farm;
		this.region = region;
		this.capacity = capacity;
		this.size = size;
	}

	public Long getId() {
		return id;
	}

	public String getRegion() {
		return region;
	}

	public Double getSize() {
		return size;
	}
	
	public Double getCapacity() {
		return capacity;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setSize(Double size) {
		this.size = size;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}

	@JsonIgnore
	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}
	
	@JsonIgnore
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Silo))
			return false;

		Silo other = (Silo) obj;
		return Objects.equals(this.id, other.id);
	}
}
