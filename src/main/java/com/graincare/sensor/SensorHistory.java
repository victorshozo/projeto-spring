package com.graincare.sensor;

import java.util.Calendar;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.graincare.silos.SiloHistory;

@Entity
@Table(name = "sensor_history")
public class SensorHistory {
	@Id
	@GeneratedValue
	private Long id;
	@OneToOne
	@NotNull
	private Sensor sensor;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "silo_history_id")
	@JsonBackReference
	private SiloHistory siloHistory;
	@Column(name = "temperature")
	private Double temperature;
	@Column(name = "distance")
	private Double distance;
	@Column(name = "humidity")
	private Double humidity;
	@Column(name = "updated_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Calendar updatedAt = Calendar.getInstance();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public SiloHistory getSiloHistory() {
		return siloHistory;
	}

	public void setSiloHistory(SiloHistory siloHistory) {
		this.siloHistory = siloHistory;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Double getHumidity() {
		return humidity;
	}

	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

	public Calendar getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Calendar updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof SensorHistory))
			return false;

		SensorHistory other = (SensorHistory) obj;
		return Objects.equals(this.getSensor(), other.getSensor());
	}
}
