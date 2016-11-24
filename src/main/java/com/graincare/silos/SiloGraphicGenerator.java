package com.graincare.silos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SiloGraphicGenerator {
	
	public SiloGraphicDTO generateGraphicFor(List<Object[]> results, Date startDate, Date endDate) {
		List<SiloGraphPointDTO> temperatures = new ArrayList<>();
		List<SiloGraphPointDTO> humidities = new ArrayList<>();
		
		int i = 0; 
		for (Object[] result : results) {
			Double averageHumidity = (Double) result[0];
			Double averageTemperature = (Double) result[1];
			
			i++;
			temperatures.add(new SiloGraphPointDTO(i, averageTemperature));
			humidities.add(new SiloGraphPointDTO(i, averageHumidity));
		}
		
		return new SiloGraphicDTO(i, temperatures, humidities, startDate, endDate);
	}
}
