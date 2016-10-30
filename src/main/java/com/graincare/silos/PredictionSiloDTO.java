package com.graincare.silos;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PredictionSiloDTO {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Calendar date;

	public PredictionSiloDTO(Calendar date) {
		this.date = date;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

}
