package com.graincare.farm;

public class FarmDTO {
	private Long id;
	private String name;
	
	FarmDTO(){}
	
	public FarmDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
