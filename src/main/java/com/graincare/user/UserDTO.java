package com.graincare.user;

import java.util.List;

public class UserDTO {
	private String email;
	private String password;
	private List<String> farmNames;

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public List<String> getFarmNames() {
		return farmNames;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFarmNames(List<String> farmNames) {
		this.farmNames = farmNames;
	}

}
