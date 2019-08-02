package com.engin.focab.services.impl;

import org.springframework.stereotype.Component;

import com.engin.focab.jpa.Customer;
import com.engin.focab.services.SessionService;
@Component
public class DefaultSessionService implements SessionService {

	@Override
	public Customer getCurrentCustomer() {
		//TODO implement session
		return new Customer("engin");
	}

}
