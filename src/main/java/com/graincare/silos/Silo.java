package com.graincare.silos;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.graincare.graos.Grao;

@Entity
@Table(name = "silo")
public class Silo {
	@Id
	@GeneratedValue
	private Long id;
	@OneToMany(fetch = FetchType.EAGER)
	private Grao grao;
	@Column(name = "capacity")
	private Double capacity;
	@Column(name = "close_date")
	private Calendar closeDate;
	@Column(name = "open_date")
	private Calendar openDate;
	@Column(name = "region")
	private String region;

	public Silo(Long id, Grao grao, Double capacity, Calendar closeDate, Calendar openDate, String region) {
		super();
		this.id = id;
		this.grao = grao;
		this.capacity = capacity;
		this.closeDate = closeDate;
		this.openDate = openDate;
		this.region = region;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Grao getGrao() {
		return grao;
	}

	public void setGrao(Grao grao) {
		this.grao = grao;
	}

	public Double getCapacity() {
		return capacity;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}

	public Calendar getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Calendar closeDate) {
		this.closeDate = closeDate;
	}

	public Calendar getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Calendar openDate) {
		this.openDate = openDate;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

}
