package com.engin.focab.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engin.focab.dtos.UserDto;
import com.engin.focab.jpa.Customer;
import com.engin.focab.services.SessionService;

@RestController
//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class LoginController {

	@Autowired
	SessionService sessionService;
	@RequestMapping("/user")
	public UserDto authenticate(Principal user) {
		Customer customer = sessionService.getCurrentCustomer();
		return new UserDto(customer.getFullname(), customer.getUsername());
	}

}
