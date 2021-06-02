package com.engin.focab.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.PasswordResetToken;
import com.engin.focab.repository.PasswordResetTokenRepository;

public class CustomerService {
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	public void createPasswordResetTokenForCustomer(Customer customer, String token) {
		PasswordResetToken myToken = new PasswordResetToken(token, customer);
		passwordResetTokenRepository.save(myToken);
	}
}
