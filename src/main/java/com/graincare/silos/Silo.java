package com.graincare.silos;

import java.util.Objects;

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
	@Column(name = "region")
	private String region;
	@Column(name = "size")
	private Double size = 30d;

	@Deprecated
	Silo() {
	}

	public Long getId() {
		return id;
	}

	public String getRegion() {
		return region;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Double getSize() {
		return size;
	}

	public void setSize(Double size) {
		this.size = size;
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
