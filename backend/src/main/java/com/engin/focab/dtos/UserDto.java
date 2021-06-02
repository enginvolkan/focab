package com.engin.focab.dtos;

public class UserDto {
    private String fullname;
	private String username;
	private int level;


	public UserDto(String fullname, String username, int level) {
        this.fullname = fullname;
		this.username = username;
		this.level = level;
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
