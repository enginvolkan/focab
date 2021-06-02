package com.engin.focab.controllers;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.engin.focab.jpa.Customer;
import com.engin.focab.repository.CustomerRepository;
import com.engin.focab.repository.PasswordResetTokenRepository;
import com.engin.focab.services.impl.CustomerService;
import com.engin.focab.services.impl.GmailService;

public class ResetPasswordController {
	@Autowired
	private GmailService emailService;
	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@PostMapping("/user/resetPassword")
	public boolean resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
		try {
			Optional<Customer> user = customerRepository.findById(userEmail);
			if (user.isEmpty()) {
				throw new UsernameNotFoundException("Username could not be found in the system.");
			}
			String token = UUID.randomUUID().toString();
			customerService.createPasswordResetTokenForCustomer(user.get(), token);

			emailService.sendResetPasswordEmail(user.get().getUsername(), token);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@GetMapping("/user/validateToken")
	public boolean validateToken(HttpServletRequest request, @RequestParam("token") String token) {
//		passwordResetTokenRepository.findById(id)
		return true;
	}
}
