package com.graincare.silos;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.graincare.sensor.SensorAverage;

@Service
public class SiloPredictionDateCalculator {

	private static final int DEFAULT_DAYS_TO_OPEN = 21;

	public Calendar calculate(SiloHistory siloHistory, List<SensorAverage> averages) {
		int daysPerDegree = siloHistory.getGrao().getDaysPerDegree();
		double maxHumidity = siloHistory.getGrao().getMaxHumidity();
		double maxTemperature = siloHistory.getGrao().getMaxTemperature();
		int daysToRemove = 0;

		for (SensorAverage sensorAverage : averages) {
			if (sensorAverage.getAverageHumidity() > maxHumidity 
					|| sensorAverage.getAverageTemperature() > maxTemperature) {
				int leftoverDegrees = (int) (sensorAverage.getAverageTemperature() - maxTemperature);
				if(leftoverDegrees > 0) {
					daysToRemove += (leftoverDegrees * daysPerDegree);
				}
			}
		}
		
		if (daysToRemove > DEFAULT_DAYS_TO_OPEN) {
			daysToRemove = DEFAULT_DAYS_TO_OPEN;
		}
		
		Calendar predictionDate = siloHistory.getClosedAt();
		predictionDate.add(Calendar.DATE, DEFAULT_DAYS_TO_OPEN - daysToRemove);
		return predictionDate;
	}
}
