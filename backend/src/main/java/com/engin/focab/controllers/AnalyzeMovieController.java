package com.engin.focab.controllers;

import java.net.MalformedURLException;

import org.apache.xmlrpc.XmlRpcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.MovieAnalysisModel;
import com.engin.focab.repository.CustomerRepository;
import com.engin.focab.repository.RoleRepository;
import com.engin.focab.services.AnalysisService;
import com.engin.focab.services.SessionService;
import com.engin.focab.services.VocabularyService;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
public class AnalyzeMovieController {

	@Autowired
	private AnalysisService analysisService;
	@Autowired
	private VocabularyService vocabularyService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	RoleRepository roleRepository;

	@GetMapping("/analyzeMovie")
	@ResponseBody
	public MovieAnalysisModel analyzeMovie(@RequestParam String imdbId) throws MalformedURLException, XmlRpcException {
		Customer customer = sessionService.getCurrentCustomer();

		if (customer != null) {
			return analysisService.analyzeMovie(imdbId);
		} else {
			customer = new Customer("engin");
			customer.setEmail("envolkan@gmail.com");
			customer.setEnabled(true);
			customer.setPassword(passwordEncoder().encode("123qwe"));

//			Role admin = new Role("ADMIN");
//			customer.setRoles(new HashSet<Role>());
//			customer.getRoles().add(admin);
//
//			roleRepository.save(admin);
//			customerRepository.save(customer);

			return new MovieAnalysisModel(passwordEncoder().encode("123qwe"));

		}

	}

	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public AnalysisService getAnalysisService() {
		return analysisService;
	}

	public void setAnalysisService(AnalysisService analysisService) {
		this.analysisService = analysisService;
	}

	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

}
