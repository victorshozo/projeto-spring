package com.graincare.silos;

public class SiloGraphPointDTO {
	private long x;
	private Double y;

	public SiloGraphPointDTO(long x, Double y) {
		this.x = x;
		this.y = y;
	}

	public long getX() {
		return x;
	}

	public Double getY() {
		return y;
	}

	public void setX(long x) {
		this.x = x;
	}

	public void setY(Double y) {
		this.y = y;
	}

}
