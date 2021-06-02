package com.engin.focab.jpa;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class PasswordResetToken {

	private static final int EXPIRATION = 60 * 24;

	@Id
	private String token;

	@OneToOne(targetEntity = Customer.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "username")
	private Customer user;

	private Date expiryDate;

	public PasswordResetToken(String token, Customer user) {
		super();
		this.token = token;
		this.user = user;
	}

	public String getToken() {
		return token;
	}


	public Customer getUser() {
		return user;
	}


	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

}