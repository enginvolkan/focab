package com.engin.focab.jpa;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.joda.time.DateTime;

/**
 * Implementation of
 * https://www.baeldung.com/spring-security-registration-i-forgot-my-password
 *
 * @author Engin Volkan
 *
 */
@Entity
public class PasswordResetToken {

	private static final int EXPIRATION = 24;

	@Id
	private String token;

	@OneToOne(targetEntity = Customer.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "username")
	private Customer user;

	private Date expiryDate;

	public PasswordResetToken() {
		super();
	}

	public PasswordResetToken(String token, Customer user) {
		super();
		this.token = token;
		this.user = user;
		this.expiryDate = new DateTime().plusHours(EXPIRATION).toDate();

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