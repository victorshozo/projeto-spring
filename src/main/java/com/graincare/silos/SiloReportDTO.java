package com.graincare.silos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.graincare.configuration.CustomDoubleSerializer;

public class SiloReportDTO {
	private Long siloId;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date reportStart;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date reportEnd;
	
	private String farmName;
	private List<SiloReport> data;
	
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	@JsonSerialize(using = CustomDoubleSerializer.class)
	private Double totalAverageHumidity;
	
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	@JsonSerialize(using = CustomDoubleSerializer.class)
	private Double totalAverageTemperature;
	
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	@JsonSerialize(using = CustomDoubleSerializer.class)
	private Double totalCapacityUsed;
	
	private List<SiloReportProfit> profit = new ArrayList<>();

	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	@JsonSerialize(using = CustomDoubleSerializer.class)
	private Double totalProfit;
	
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	@JsonSerialize(using = CustomDoubleSerializer.class)
	private Double totalWeight;

	public double getTotalProfit() {
		return totalProfit;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalProfit(Double totalProfit) {
		this.totalProfit = totalProfit;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Long getSiloId() {
		return siloId;
	}

	public Date getReportStart() {
		return reportStart;
	}

	public Date getReportEnd() {
		return reportEnd;
	}

	public String getFarmName() {
		return farmName;
	}

	public List<SiloReport> getData() {
		return data;
	}

	public Double getTotalAverageTemperature() {
		return totalAverageTemperature;
	}

	public Double getTotalAverageHumidity() {
		return totalAverageHumidity;
	}

	public Double getTotalCapacityUsed() {
		return totalCapacityUsed;
	}

	public List<SiloReportProfit> getProfit() {
		return profit;
	}

	public void setSiloId(Long siloId) {
		this.siloId = siloId;
	}

	public void setReportStart(Date reportStart) {
		this.reportStart = reportStart;
	}

	public void setReportEnd(Date reportEnd) {
		this.reportEnd = reportEnd;
	}

	public void setFarmName(String farmName) {
		this.farmName = farmName;
	}

	public void setData(List<SiloReport> data) {
		this.data = data;
	}

	public void setTotalAverageTemperature(Double totalAverageTemperature) {
		this.totalAverageTemperature = totalAverageTemperature;
	}

	public void setTotalAverageHumidity(Double totalAverageHumidity) {
		this.totalAverageHumidity = totalAverageHumidity;
	}

	public void setTotalCapacityUsed(Double totalCapacityUsed) {
		this.totalCapacityUsed = totalCapacityUsed;
	}

	public void setProfit(List<SiloReportProfit> profit) {
		this.profit = profit;
	}

}