package com.engin.focab.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class GmailService {

    @Autowired
    private JavaMailSender emailSender;
	@Value("${spring.mail.username}")
	private String emailAddress;
	@Value("${server.storefrontPath}")
	private String storefrontPath;

	public void sendProfileUpdateEmail(String to) {
		sendSimpleMessage(to, "Lexibag Profile Updated", "You've just updated your Lexibag profile!");
	}

	public void sendResetPasswordEmail(String to, String token) {
		String url = storefrontPath + "/resetPassword/" + token;
		sendSimpleMessage(to, "Lexibag Password Reset", "Please use the link below to update your password. \n " + url);
	}

	private void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(emailAddress);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}