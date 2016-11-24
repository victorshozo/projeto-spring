package com.graincare.sensor;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;

import org.springframework.stereotype.Service;

@Service
public class SensorAverageService {

	public SensorAverage getSensorAverageFor(Object[] result) {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(((Date) result[0]).getTime());

		int quantityOfTemperatures = ((BigDecimal) result[1]).intValue();
		Double averageTemperature = (Double) result[2];
		Double averageHumidity = (Double) result[3];

		SensorAverage average = new SensorAverage(date, quantityOfTemperatures, averageTemperature, averageHumidity);
		return average;
	}
}
