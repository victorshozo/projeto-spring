package com.graincare.silos;

public class SiloReportProfit {
	private Double totalWeight;
	private String grain;
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