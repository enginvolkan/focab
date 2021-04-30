package com.engin.focab.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.corpus.LexiModel;
import com.engin.focab.services.KnownWordsService;
import com.engin.focab.services.LexiDetailsService;
import com.engin.focab.services.SessionService;

//@CrossOrigin(origins = "http://localhost:4200", allowCredentials="true")
@RestController
public class KnownWordsController {

	@Autowired
	private KnownWordsService knownWordsService;
	@Autowired
	private LexiDetailsService lexiDetailsService;
	@Autowired
	private SessionService sessionService;


	@PostMapping("/addKnownWord")
	@ResponseBody
	public boolean addKnownWord(@RequestParam String entry) {
		Customer customer = sessionService.getCurrentCustomer();
		Optional<LexiModel> searchResult = lexiDetailsService.findLexi(entry);
		LexiModel lexiModel;

		if(!searchResult.isPresent()) {
			lexiModel = lexiDetailsService.createVocabulary(entry);
		}else {
			lexiModel = searchResult.get();
		}

		return knownWordsService.addKnownWord(lexiModel, customer);
	}

	@PostMapping("/removeKnownWord")
	@ResponseBody
	public boolean removeKnownWord(@RequestParam String entry) {
		Customer customer = sessionService.getCurrentCustomer();
		Optional<LexiModel> searchResult = lexiDetailsService.findLexi(entry);

		if (searchResult.isPresent()) {
			return knownWordsService.removeKnownWord(searchResult.get(), customer);
		}else {
			return false;
		}

	}

	@GetMapping("/getKnownWords")
	@ResponseBody
	public List<String> getKnownWords() {
		Customer customer = sessionService.getCurrentCustomer();

		return knownWordsService.getKnownWords(customer);
	}


}
