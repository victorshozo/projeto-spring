package com.graincare.user;

public class UserListDTO {
	private Long id;
	private String email;
	private int farmQuantity;
	
	public UserListDTO(Long id, String email, int farmQuantity) {
		this.id = id;
		this.email = email;
		this.farmQuantity = farmQuantity;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public int getFarmQuantity() {
		return farmQuantity;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFarmQuantity(int farmQuantity) {
		this.farmQuantity = farmQuantity;
	}

}
