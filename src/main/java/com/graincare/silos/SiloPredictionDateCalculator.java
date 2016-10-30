package com.graincare.silos;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.graincare.beacon.BeaconAverage;

@Service
public class SiloPredictionDateCalculator {
	
	private static final int DEFAULT_DAYS_TO_OPEN = 21;

	public Calendar calculate(SiloHistory siloHistory, List<BeaconAverage> averages) {
		Double averageTemperature = 0.0;
		Double averageHumidity = 0.0;
		for (BeaconAverage average : averages) {
			averageTemperature += average.getAverageTemperature();
			averageHumidity += average.getAverageHumidity();
		}
		averageTemperature = (averageTemperature / averages.size());
		averageHumidity = (averageHumidity / averages.size());
		
		Double maxTemperature = siloHistory.getGrao().getMaxTemperature();
		Double maxHumidity = siloHistory.getGrao().getMaxHumidity();
		
		Calendar predictionDate = siloHistory.getClosedAt();
		if (averages.size() <= 1 || (averageTemperature <= maxTemperature && averageHumidity <= maxHumidity)) {
			predictionDate.add(Calendar.DATE, DEFAULT_DAYS_TO_OPEN);
		} else {
			int leftoverDegrees = (int) (averageTemperature - maxTemperature);
			leftoverDegrees = leftoverDegrees < 0 ? leftoverDegrees * -1 : leftoverDegrees;
			
			int daysPerDegree = siloHistory.getGrao().getDaysPerDegree();
			
			int daysToRemove = daysPerDegree * leftoverDegrees;
			predictionDate.add(Calendar.DATE, DEFAULT_DAYS_TO_OPEN - daysToRemove);
		}
		return predictionDate;
	}
}
