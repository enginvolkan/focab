package com.engin.focab.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authority")
public class Authority {

	@Id
	private String authority;


	public Authority() {
		super();
	}

	public Authority(String authority) {
		super();
		this.authority = authority;
	}


	public String getAuthority() {
		return authority;
	}


	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
