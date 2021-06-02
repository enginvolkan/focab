package com.engin.focab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.engin.focab.dtos.UserDto;
import com.engin.focab.jpa.Customer;
import com.engin.focab.services.SessionService;
import com.engin.focab.services.impl.GmailService;

@RestController
//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ProfileController {

	@Autowired
	SessionService sessionService;
	@Autowired
	GmailService emailService;

	@PostMapping("/updateProfile")
	@ResponseBody
	public UserDto update(@RequestBody UserDto user) {
		Customer customer = sessionService.getCurrentCustomer();
		customer.setFullname(user.getFullname());
		customer.setLevel(user.getLevel());
		emailService.sendProfileUpdateEmail(customer.getUsername());

		return new UserDto(customer.getFullname(), customer.getUsername(), customer.getLevel());
	}

}
