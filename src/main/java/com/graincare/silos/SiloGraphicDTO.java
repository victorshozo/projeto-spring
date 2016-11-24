package com.graincare.silos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SiloGraphicDTO {
	private long days;
	private List<SiloGraphPointDTO> temperatures = new ArrayList<>();
	private List<SiloGraphPointDTO> humidities = new ArrayList<>();
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date startDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date endDate;

	public SiloGraphicDTO(long days, List<SiloGraphPointDTO> temperatures, List<SiloGraphPointDTO> humidities,
			Date startDate, Date endDate) {
		this.days = days;
		this.temperatures = temperatures;
		this.humidities = humidities;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public long getDays() {
		return days;
	}

	public List<SiloGraphPointDTO> getTemperatures() {
		return temperatures;
	}

	public List<SiloGraphPointDTO> getHumidities() {
		return humidities;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setDays(long days) {
		this.days = days;
	}

	public void setTemperatures(List<SiloGraphPointDTO> temperatures) {
		this.temperatures = temperatures;
	}

	public void setHumidities(List<SiloGraphPointDTO> humidities) {
		this.humidities = humidities;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
