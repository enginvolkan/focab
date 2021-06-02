package com.engin.focab.controllers;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.xmlrpc.XmlRpcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.SubtitleModel;
import com.engin.focab.jpa.SummerizedMovieAnalysisModel;
import com.engin.focab.repository.AuthorityRepository;
import com.engin.focab.repository.CustomerRepository;
import com.engin.focab.services.AnalysisService;
import com.engin.focab.services.PersonalizationService;
import com.engin.focab.services.SessionService;

//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
public class AnalyzeMovieController {

	@Autowired
	private AnalysisService analysisService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	AuthorityRepository authorityRepository;
	@Autowired
	PersonalizationService personalizationService;

	@GetMapping("/analyzeMovie")
	@ResponseBody
	public SummerizedMovieAnalysisModel analyzeMovie(@RequestParam String imdbId)
			throws MalformedURLException, XmlRpcException {
		Customer customer = sessionService.getCurrentCustomer();

		if (customer != null) {
			SummerizedMovieAnalysisModel analysis = analysisService.analyzeMovie(imdbId);
			return personalizationService.personalize(analysis, customer);
		} else {
			return new SummerizedMovieAnalysisModel("");
		}

	}

	@GetMapping("/getFullSubtitles")
	@ResponseBody
	public List<SubtitleModel> fullSubtitles(@RequestParam String imdbId) {
		Customer customer = sessionService.getCurrentCustomer();

		if (customer != null) {
			List<SubtitleModel> subtitles = analysisService.getFullSubtitles(imdbId);
			return subtitles;
		} else {
			return List.of(new SubtitleModel());
		}

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
