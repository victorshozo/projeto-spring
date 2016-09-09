package main;

public class Beacon {
	private Integer beaconID;
	private Integer beaconBattery;
	private long beaconTemperature;
	private long beaconDistance;
	private Integer siloID;

	public Beacon(Integer beaconID, Integer beaconBattery, long beaconTemperature, Integer siloID, long beaconDistance) {
		this.beaconID = beaconID;
		this.beaconBattery = beaconBattery;
		this.beaconTemperature = beaconTemperature;
		this.siloID = siloID;
		this.beaconDistance = beaconDistance;
	}

	public Integer getBeaconID() {
		return beaconID;
	}

	public Integer getBeaconBattery() {
		return beaconBattery;
	}

	public long getBeaconTemperature() {
		return beaconTemperature;
	}

	public long getBeaconDistance() {
		return beaconDistance;
	}

	public Integer getSiloID() {
		return siloID;
	}

	public void setBeaconDistance(long beaconDistance) {
		this.beaconDistance = beaconDistance;
	}

	public void setSiloID(Integer siloID) {
		this.siloID = siloID;
	}

	public void setBeaconBattery(Integer beaconBattery) {
		this.beaconBattery = beaconBattery;
	}

	public void setBeaconID(Integer beaconID) {
		this.beaconID = beaconID;
	}

	public void setBeaconTemperature(long beaconTemperature) {
		this.beaconTemperature = beaconTemperature;
	}
}
