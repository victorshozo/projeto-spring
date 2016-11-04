package com.graincare.beacon;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;

import org.springframework.stereotype.Service;

@Service
public class BeaconAverageService {

	public BeaconAverage getBeaconAverageFor(Object[] result) {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(((Date) result[0]).getTime());

		int quantityOfTemperatures = ((BigDecimal) result[1]).intValue();
		Double averageTemperature = (Double) result[2];
		Double averageHumidity = (Double) result[3];

		BeaconAverage average = new BeaconAverage(date, quantityOfTemperatures, averageTemperature, averageHumidity);
		return average;
	}
}
