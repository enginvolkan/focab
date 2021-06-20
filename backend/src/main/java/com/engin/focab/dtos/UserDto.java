package com.engin.focab.dtos;

public class UserDto {
    private String fullname;
	private String username;
	private int level;
	private String password;


	public UserDto(String fullname, String username, int level) {
        this.fullname = fullname;
		this.username = username;
		this.level = level;
    }

	public UserDto(String fullname, String username, int level, String password) {
		super();
		this.fullname = fullname;
		this.username = username;
		this.level = level;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
