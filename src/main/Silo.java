package main;

public class Silo {

	private Integer siloID;
	private Integer graoID;
	private Double siloCapacity;
	// MUDAR DATAS PARA DATE
	private String siloDataClose;
	private String siloDataOpen;
	private String siloRegion;

	public Silo(Integer siloID, Integer graoID, Double siloCapacity, String siloDataClose, String siloDataOpen,
			String siloRegion) {
		this.siloID = siloID;
		this.graoID = graoID;
		this.siloCapacity = siloCapacity;
		this.siloRegion = siloRegion;
		this.siloDataClose = siloDataClose;
		this.siloDataOpen = siloDataOpen;
	}

	public Integer getSiloID() {
		return siloID;
	}

	public Integer getGraoID() {
		return graoID;
	}

	public String getSiloDataClose() {
		return siloDataClose;
	}

	public String getSiloDataOpen() {
		return siloDataOpen;
	}

	public Double getSiloCapacity() {
		return siloCapacity;
	}

	public String getSiloRegion() {
		return siloRegion;
	}

	public void setSiloID(Integer siloID) {
		this.siloID = siloID;
	}

	public void setGraoID(Integer graoID) {
		this.graoID = graoID;
	}

	public void setSiloRegion(String siloRegion) {
		this.siloRegion = siloRegion;
	}

	public void setSiloCapacity(Double siloCapacity) {
		this.siloCapacity = siloCapacity;
	}

	public void setSiloDataClose(String siloDataClose) {
		this.siloDataClose = siloDataClose;
	}

	public void setSiloDataOpen(String siloDataOpen) {
		this.siloDataOpen = siloDataOpen;
	}
}
