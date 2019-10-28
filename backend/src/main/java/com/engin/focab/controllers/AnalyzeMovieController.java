package com.engin.focab.controllers;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.apache.xmlrpc.XmlRpcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.FavoriteEntry;
import com.engin.focab.jpa.MovieAnalysisModel;
import com.engin.focab.jpa.Vocabulary;
import com.engin.focab.services.AnalysisService;
import com.engin.focab.services.FavoriteService;
import com.engin.focab.services.SessionService;
import com.engin.focab.services.VocabularyService;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials="true")
@RestController
public class AnalyzeMovieController {

	@Autowired
	private AnalysisService analysisService;
	@Autowired
	private VocabularyService vocabularyService;
	@Autowired
	private SessionService sessionService;



	@GetMapping("/analyzeMovie")
	@ResponseBody
	public MovieAnalysisModel analyzeMovie(@RequestParam String imdbId) throws MalformedURLException, XmlRpcException {
		Customer customer = sessionService.getCurrentCustomer();
		
		if(customer!=null) {
			return analysisService.analyzeMovie(imdbId);
		}else {
			return null;

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
