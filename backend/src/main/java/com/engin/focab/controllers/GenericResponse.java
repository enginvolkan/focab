package com.engin.focab.controllers;

public class GenericResponse {
	private String message;
	private boolean success;

	public String getMessage() {
		return message;
	}

	public boolean isSuccess() {
		return success;
	}

	public GenericResponse(String message, boolean success) {
		super();
		this.message = message;
		this.success = success;
	}

}
