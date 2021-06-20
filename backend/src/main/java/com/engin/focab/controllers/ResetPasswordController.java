package com.engin.focab.controllers;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.PasswordChangeRequestModel;
import com.engin.focab.jpa.PasswordResetToken;
import com.engin.focab.repository.CustomerRepository;
import com.engin.focab.repository.PasswordResetTokenRepository;
import com.engin.focab.services.impl.CustomerService;
import com.engin.focab.services.impl.GmailService;

@RestController
public class ResetPasswordController {
	@Autowired
	private GmailService emailService;
	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@PostMapping("/resetPassword")
	public GenericResponse resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {

			Optional<Customer> user = customerRepository.findById(userEmail);
			if (user.isEmpty()) {
			return new GenericResponse("Username could not be found in the system.", false);
			}
			String token = UUID.randomUUID().toString();
			customerService.createPasswordResetTokenForCustomer(user.get(), token);

			emailService.sendResetPasswordEmail(user.get().getUsername(), token);
		return new GenericResponse("An e-mail has been sent to your address to reset your password.", true);

	}

	@GetMapping("/validatePasswordResetToken")
	public GenericResponse validateToken(HttpServletRequest request, @RequestParam("token") String token) {
		Optional<PasswordResetToken> dbtoken = passwordResetTokenRepository.findById(token);
		if (dbtoken.isEmpty()) {
			return new GenericResponse("Token is not valid", false);
		}
		if (dbtoken.get().getExpiryDate().before(new Date())) {
			return new GenericResponse("Token is expired", false);
		}
		return new GenericResponse("Token validated", true);
	}

	@PostMapping("/changePasswordForToken")
	public GenericResponse changePasswordForToken(HttpServletRequest request,
			@RequestBody PasswordChangeRequestModel changeRequest) {

		if (StringUtils.isBlank(changeRequest.getToken())) {
			return new GenericResponse("Token should be sent for password reset", false);
		}
		Optional<PasswordResetToken> dbtoken = passwordResetTokenRepository.findById(changeRequest.getToken());

		if (dbtoken.isEmpty()) {
			return new GenericResponse("Token is not found", false);
		}
		if (dbtoken.get().getExpiryDate().before(new Date())) {
			return new GenericResponse("Token is expired", false);
		}

		if (!customerService.changePassword(dbtoken.get().getUser(), changeRequest.getPassword())) {
			return new GenericResponse("Inernal problem, password could not be changed", false);
		}

		return new GenericResponse("Password changed, please login to continue", true);
	}
}
