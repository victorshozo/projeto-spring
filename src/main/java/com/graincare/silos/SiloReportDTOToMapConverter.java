package com.graincare.silos;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class SiloReportDTOToMapConverter {
	
	public Map<String, Object> convert(SiloReportDTO siloReportDTO) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		Map<String, Object> map = new HashMap<>();
		map.put("farmName", siloReportDTO.getFarmName());
		map.put("siloId", siloReportDTO.getSiloId().toString());
		map.put("startDate", dateFormat.format(siloReportDTO.getReportStart()));
		map.put("endDate", dateFormat.format(siloReportDTO.getReportEnd()));
		map.put("data", siloReportDTO.getData());
		map.put("profit", siloReportDTO.getProfit());
		
		return map;
	} 
}
