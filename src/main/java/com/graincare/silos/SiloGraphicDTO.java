package com.graincare.silos;

import java.util.ArrayList;
import java.util.List;

public class SiloGraphicDTO {
	private long days;
	private List<SiloGraphPointDTO> temperatures = new ArrayList<>();
	private List<SiloGraphPointDTO> humidities = new ArrayList<>();

	public SiloGraphicDTO(long days, List<SiloGraphPointDTO> temperatures, List<SiloGraphPointDTO> humidities) {
		this.days = days;
		this.temperatures = temperatures;
		this.humidities = humidities;
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

	public void setDays(long days) {
		this.days = days;
	}

	public void setTemperatures(List<SiloGraphPointDTO> temperatures) {
		this.temperatures = temperatures;
	}

	public void setHumidities(List<SiloGraphPointDTO> humidities) {
		this.humidities = humidities;
	}

}
