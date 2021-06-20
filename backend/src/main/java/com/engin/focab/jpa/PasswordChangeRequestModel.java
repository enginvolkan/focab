package com.engin.focab.jpa;

public class PasswordChangeRequestModel {
	private String token;
	private String password;

	public PasswordChangeRequestModel(String token, String password) {
		super();
		this.token = token;
		this.password = password;
	}

	public PasswordChangeRequestModel() {
		super();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
