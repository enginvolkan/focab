package com.engin.focab.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.engin.focab.jpa.Customer;
import com.engin.focab.repository.AuthorityRepository;
import com.engin.focab.repository.CustomerRepository;
import com.engin.focab.services.SessionService;
@Component
public class DefaultSessionService implements SessionService {
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	AuthorityRepository authorityRepository;

	@Override
	public Customer getCurrentCustomer() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			  username = ((UserDetails)principal).getUsername();
			} else {
			  username = principal.toString();
			}

		return customerRepository.findById(username).get();
	}

}
