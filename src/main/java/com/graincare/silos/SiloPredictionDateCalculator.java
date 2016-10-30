package com.graincare.silos;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.graincare.beacon.BeaconAvarage;

@Service
public class SiloPredictionDateCalculator {
	
	private static final int DEFAULT_DAYS_TO_OPEN = 21;

	public Calendar calculate(SiloHistory siloHistory, List<BeaconAvarage> avarages) {
		Double avarageTemperature = 0.0;
		Double avarageHumidity = 0.0;
		for (BeaconAvarage avarage : avarages) {
			avarageTemperature += avarage.getAvarageTemperature();
			avarageHumidity += avarage.getAvarageHumidity();
		}
		avarageTemperature = (avarageTemperature / avarages.size());
		avarageHumidity = (avarageHumidity / avarages.size());
		
		Double maxTemperature = siloHistory.getGrao().getMaxTemperature();
		Double maxHumidity = siloHistory.getGrao().getMaxHumidity();
		
		Calendar predictionDate = siloHistory.getClosedAt();
		if (avarages.size() <= 1 || (avarageTemperature <= maxTemperature && avarageHumidity <= maxHumidity)) {
			predictionDate.add(Calendar.DATE, DEFAULT_DAYS_TO_OPEN);
		} else {
			int leftoverDegrees = (int) (avarageTemperature - maxTemperature);
			leftoverDegrees = leftoverDegrees < 0 ? leftoverDegrees * -1 : leftoverDegrees;
			
			int daysPerDegree = siloHistory.getGrao().getDaysPerDegree();
			
			int daysToRemove = daysPerDegree * leftoverDegrees;
			predictionDate.add(Calendar.DATE, DEFAULT_DAYS_TO_OPEN - daysToRemove);
		}
		return predictionDate;
	}
}
