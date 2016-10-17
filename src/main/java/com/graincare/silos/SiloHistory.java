package com.graincare.silos;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.graincare.beacon.BeaconHistory;
import com.graincare.graos.GrainType;

@Entity
@Table(name = "silo_history")
public class SiloHistory {

	@Id
	@GeneratedValue
	private Long id;
	@NotNull
	@Enumerated(EnumType.STRING)
	private GrainType grao;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "siloHistory", cascade = CascadeType.MERGE)
	@JsonManagedReference
	private List<BeaconHistory> beaconsHistory;
	@Column(name = "opened_at")
	private Calendar openedAt;
	@Column(name = "closed_at")
	private Calendar closedAt;
	@Column(name = "open")
	private Boolean open;
	@OneToOne
	@NotNull
	private Silo silo;

	@Deprecated
	SiloHistory() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GrainType getGrao() {
		return grao;
	}

	public void setGrao(GrainType grao) {
		this.grao = grao;
	}

	public List<BeaconHistory> getBeaconsHistory() {
		return beaconsHistory;
	}

	public void setBeaconsHistory(List<BeaconHistory> beaconsHistory) {
		this.beaconsHistory = beaconsHistory;
	}

	public Calendar getOpenedAt() {
		return openedAt;
	}

	public void setOpenedAt(Calendar openedAt) {
		this.openedAt = openedAt;
	}

	public Calendar getClosedAt() {
		return closedAt;
	}

	public void setClosedAt(Calendar closedAt) {
		this.closedAt = closedAt;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Silo getSilo() {
		return silo;
	}

	public void setSilo(Silo silo) {
		this.silo = silo;
	}

}
