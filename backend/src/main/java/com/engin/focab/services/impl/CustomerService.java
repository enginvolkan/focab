package com.engin.focab.services.impl;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.PasswordResetToken;
import com.engin.focab.repository.CustomerRepository;
import com.engin.focab.repository.PasswordResetTokenRepository;

@Component
public class CustomerService {
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public void createPasswordResetTokenForCustomer(Customer customer, String token) {
		PasswordResetToken myToken = new PasswordResetToken(token, customer);
		passwordResetTokenRepository.save(myToken);
	}

	public boolean changePassword(Customer customer, String password) {
		try {
			validatePassword(password);
			customer.setPassword(passwordEncoder.encode(password));
			customerRepository.save(customer);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	protected void validatePassword(String password) {
		Assert.assertTrue(StringUtils.isNotBlank(password));
		Assert.assertTrue(password.matches("^(?=.*[A-Za-z.,@$!%*#?&])(?=.*\\d)[A-Za-z\\d.,@$!%*#?&]{8,}$"));
	}
}
