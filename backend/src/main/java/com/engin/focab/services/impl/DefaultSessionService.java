package com.engin.focab.services.impl;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.engin.focab.jpa.Customer;
import com.engin.focab.repository.CustomerRepository;
import com.engin.focab.repository.RoleRepository;
import com.engin.focab.services.SessionService;
@Component
public class DefaultSessionService implements SessionService {
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public Customer getCurrentCustomer() {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			  username = ((UserDetails)principal).getUsername();
			} else {
			  username = principal.toString();
			}

		return customerRepository.findCustomerByEmail(username);
	}

}
