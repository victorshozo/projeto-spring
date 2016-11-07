package com.graincare.silos;

import com.fasterxml.jackson.annotation.JsonInclude;

public class SiloReportProfit {
	private Double totalWeight;
	private String grain;
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
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
