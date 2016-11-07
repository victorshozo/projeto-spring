package com.graincare.silos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graincare.graos.GrainBagPriceDAO;
import com.graincare.graos.GrainType;

@Service
public class SiloReportGenerator {
	
	@Autowired
	private GrainBagPriceDAO grainBagPriceDAO;

	public SiloReportDTO generateFor(Silo silo, List<Object[]> results, Date startDate, Date endDate) {
		List<SiloReport> siloReports = new ArrayList<>();
		Double totalAverageTemperature = 0.0;
		Double totalAverageHumidity = 0.0;
		Double totalCapacityUsed = 0.0;
		Double totalProfit = 0.0;
		Double totalWeight = 0.0;
		
		for (Object[] result : results) {
			Calendar closedAt = Calendar.getInstance();
			closedAt.setTime((Date) result[1]);
			
			Calendar openAt = Calendar.getInstance();
			openAt.setTime((Date) result[2]);
			
			String grain = (String) result[3];
			Double averageTemperature = (Double) result[4];
			Double averageHumidity = (Double) result[5];
			Double capacityPercentUsed = (Double) result[6];
			Double weight = (silo.getCapacity() * capacityPercentUsed) / 100;
			Double bagPrice = grainBagPriceDAO.getPriceFor(grain, openAt);
			Double grainPrice = (weight / 60) * bagPrice;
			
			SiloReport siloReport = new SiloReport();
			siloReport.setOpenAt(openAt);
			siloReport.setClosedAt(closedAt);
			siloReport.setGrain(grain);
			siloReport.setAverageTemperature(averageTemperature);
			siloReport.setAverageHumidity(averageHumidity);
			siloReport.setCapacityPercentUsed(capacityPercentUsed);
			siloReport.setGrainWeight(weight);
			siloReport.setGrainPrice(grainPrice);
			
			totalAverageTemperature += averageTemperature;
			totalAverageHumidity += averageHumidity;
			totalCapacityUsed += capacityPercentUsed;
			totalWeight += weight;
			totalProfit += grainPrice;
			
			siloReports.add(siloReport);
		}
		
		if(siloReports.size() > 0) {
			totalAverageTemperature /= siloReports.size();
			totalAverageHumidity /= siloReports.size();
			totalCapacityUsed /= siloReports.size();
		}
		
		SiloReportDTO siloReportDTO = new SiloReportDTO();
		siloReportDTO.setSiloId(silo.getId());
		siloReportDTO.setFarmName(silo.getFarm().getName());
		siloReportDTO.setData(siloReports);
		siloReportDTO.setTotalAverageTemperature(totalAverageTemperature);
		siloReportDTO.setTotalAverageHumidity(totalAverageHumidity);
		siloReportDTO.setTotalCapacityUsed(totalCapacityUsed);
		siloReportDTO.setProfit(calculateProfits(siloReports));
		siloReportDTO.setTotalProfit(totalProfit);
		siloReportDTO.setTotalWeight(totalWeight);
		siloReportDTO.setReportStart(startDate);
		siloReportDTO.setReportEnd(endDate);
		
		return siloReportDTO;
	}

	private List<SiloReportProfit> calculateProfits(List<SiloReport> siloReports) {
		List<SiloReportProfit> profits = new ArrayList<>();
		for (GrainType grainType : GrainType.values()) {
			Double profitValue = 0.0;
			Double totalWeightValue = 0.0;
			
			List<SiloReport> filteredReport = siloReports.stream()
					.filter(s -> grainType.getType().equals(s.getGrain()))
					.collect(Collectors.toList());
			
			for (SiloReport siloReport : filteredReport) {
				totalWeightValue += siloReport.getGrainWeight();
				profitValue += siloReport.getGrainPrice();
			}
			
			SiloReportProfit siloReportProfit = new SiloReportProfit();
			siloReportProfit.setGrain(grainType.getType());
			siloReportProfit.setProfit(profitValue);
			siloReportProfit.setTotalWeight(totalWeightValue);
			profits.add(siloReportProfit);
		}
		return profits;
	}

}
