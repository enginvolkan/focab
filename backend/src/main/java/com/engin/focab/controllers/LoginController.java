package com.engin.focab.controllers;

import java.security.Principal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engin.focab.jpa.Customer;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials="true")
public class LoginController {
	
	  @RequestMapping("/user")
	  public Principal user(Principal user) {
	    return user;
	  }

	  @PostMapping("/user")
	  public Principal x(Principal user) {
	    return user;
	  }
}
