package com.graincare.silos;

public class Silo {

	private long siloID;
	private long graoID;
	private Double siloCapacity;
	private String siloDataClose;
	private String siloDataOpen;
	private String siloRegion;

	public Silo(long siloID, long graoID, Double siloCapacity, String siloDataClose, String siloDataOpen,
			String siloRegion) {
		this.setSiloID(siloID);
		this.setGraoID(graoID);
		this.setSiloCapacity(siloCapacity);
		this.setSiloRegion(siloRegion);
		this.setSiloDataClose(siloDataClose);
		this.setSiloDataOpen(siloDataOpen);
	}

	public long getSiloID() {
		return siloID;
	}

	public void setSiloID(long siloID) {
		this.siloID = siloID;
	}

	public long getGraoID() {
		return graoID;
	}

	public void setGraoID(long graoID) {
		this.graoID = graoID;
	}

	public Double getSiloCapacity() {
		return siloCapacity;
	}

	public void setSiloCapacity(Double siloCapacity) {
		this.siloCapacity = siloCapacity;
	}

	public String getSiloRegion() {
		return siloRegion;
	}

	public void setSiloRegion(String siloRegion) {
		this.siloRegion = siloRegion;
	}

	public String getSiloDataClose() {
		return siloDataClose;
	}

	public void setSiloDataClose(String siloDataClose) {
		this.siloDataClose = siloDataClose;
	}

	public String getSiloDataOpen() {
		return siloDataOpen;
	}

	public void setSiloDataOpen(String siloDataOpen) {
		this.siloDataOpen = siloDataOpen;
	}
}
