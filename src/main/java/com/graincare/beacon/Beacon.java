package com.graincare.beacon;


public class Beacon {
	private Integer beaconID;
	private long beaconTemperature;
	private long beaconDistance;
	private Integer siloID;
	private long beaconHumidity;

	public Beacon(Integer beaconID, Integer siloID, long beaconTemperature, long beaconHumidity, long beaconDistance) {
		this.setBeaconID(beaconID);
		this.setSiloID(siloID);
		this.setBeaconTemperature(beaconTemperature);
		this.setBeaconHumidity(beaconHumidity);
		this.setBeaconDistance(beaconDistance);
	}

	public Integer getBeaconID() {
		return beaconID;
	}

	public void setBeaconID(Integer beaconID) {
		this.beaconID = beaconID;
	}
	public long getBeaconTemperature() {
		return beaconTemperature;
	}

	public void setBeaconTemperature(long beaconTemperature) {
		this.beaconTemperature = beaconTemperature;
	}

	public long getBeaconDistance() {
		return beaconDistance;
	}

	public void setBeaconDistance(long beaconDistance) {
		this.beaconDistance = beaconDistance;
	}

	public Integer getSiloID() {
		return siloID;
	}

	public void setSiloID(Integer siloID) {
		this.siloID = siloID;
	}

	public long getBeaconHumidity() {
		return beaconHumidity;
	}

	public void setBeaconHumidity(long beaconHumidity) {
		this.beaconHumidity = beaconHumidity;
	}	
}