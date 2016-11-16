package com.graincare.silos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.graincare.configuration.CustomDoubleSerializer;

public class SiloReportProfit {
	
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	@JsonSerialize(using = CustomDoubleSerializer.class)
	private Double totalWeight;
	
	private String grain;
	
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	@JsonSerialize(using = CustomDoubleSerializer.class)
	private Double profit;

	public Double getTotalWeight() {
		return totalWeight;
	}

	public String getGrain() {
		return grain;
	}

	public Double getProfit() {
		return profit;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public void setGrain(String grain) {
		this.grain = grain;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

}
