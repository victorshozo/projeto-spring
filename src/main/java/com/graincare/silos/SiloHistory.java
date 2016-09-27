package com.graincare.silos;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.graincare.beacon.BeaconHistory;
import com.graincare.graos.Grao;

@Entity
@Table(name = "silo_history")
public class SiloHistory {

	@Id
	@GeneratedValue
	private Long id;
	@OneToOne
	@NotNull
	private Grao grao;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "siloHistory")
	@JsonManagedReference
	private List<BeaconHistory> beacons;
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

	public Grao getGrao() {
		return grao;
	}

	public void setGrao(Grao grao) {
		this.grao = grao;
	}

	public List<BeaconHistory> getBeacons() {
		return beacons;
	}

	public void setBeacons(List<BeaconHistory> beacons) {
		this.beacons = beacons;
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
