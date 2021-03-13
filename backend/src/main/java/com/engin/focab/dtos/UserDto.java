package com.engin.focab.dtos;

public class UserDto {
    private String fullname;
	private String username;


	public UserDto(String fullname, String username) {
        this.fullname = fullname;
		this.username = username;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


}
