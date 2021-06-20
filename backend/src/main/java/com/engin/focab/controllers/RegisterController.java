package com.engin.focab.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.engin.focab.dtos.UserDto;
import com.engin.focab.jpa.Customer;
import com.engin.focab.repository.CustomerRepository;
import com.engin.focab.services.SessionService;
import com.engin.focab.services.impl.CustomerService;

@RestController
//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class RegisterController {

	@Autowired
	SessionService sessionService;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CustomerService customerService;

	@PostMapping("/register")
	public GenericResponse register(HttpServletRequest request, @RequestBody UserDto userDto) {

		Customer customer = new Customer(userDto.getUsername());
		customer.setFullname(userDto.getFullname());
		customer.setLevel(0);
		if (customerService.changePassword(customer, userDto.getPassword())) {
			return new GenericResponse("Registration completed", true);
		}

		return new GenericResponse("Internal error, registration could not be completed, please try again.", true);

	}

}
