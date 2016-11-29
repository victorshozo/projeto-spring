package com.graincare.user;

public class PasswordChangeDTO {

	private String password;
	private String checkPassword;

	public String getPassword() {
		return password;
	}

	public String getCheckPassword() {
		return checkPassword;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCheckPassword(String checkPassword) {
		this.checkPassword = checkPassword;
	}

}
