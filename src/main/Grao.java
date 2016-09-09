package main;

public class Grao {

	private Integer graoID;
	private String graoTipo;
	private Integer graoTempMax;

	public Grao(Integer graoID, String graoTipo, Integer graoTempMax) {
		this.graoID = graoID;
		this.graoTempMax = graoTempMax;
		this.graoTipo = graoTipo;
	}

	public Integer getGraoID() {
		return graoID;
	}

	public Integer getGraoTempMax() {
		return graoTempMax;
	}

	public String getGraoTipo() {
		return graoTipo;
	}

	public void setGraoID(Integer graoID) {
		this.graoID = graoID;
	}

	public void setGraoTempMax(Integer graoTempMax) {
		this.graoTempMax = graoTempMax;
	}

	public void setGraoTipo(String graoTipo) {
		this.graoTipo = graoTipo;
	}
}
