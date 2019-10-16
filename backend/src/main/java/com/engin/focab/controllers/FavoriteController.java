package com.engin.focab.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.FavoriteEntry;
import com.engin.focab.jpa.Vocabulary;
import com.engin.focab.services.FavoriteService;
import com.engin.focab.services.SessionService;
import com.engin.focab.services.VocabularyService;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials="true")
@RestController
public class FavoriteController {

	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private VocabularyService vocabularyService;
	@Autowired
	private SessionService sessionService;


	@PostMapping("/addFavorite")
	@ResponseBody
	public boolean addFavorite(@RequestParam String entry) {
		Customer customer = sessionService.getCurrentCustomer();
		Optional<Vocabulary> searchResult = vocabularyService.findVocabulary(entry);
		Vocabulary vocabulary;
		
		if(!searchResult.isPresent()) {
			vocabulary = vocabularyService.createVocabulary(entry);
		}else {
			vocabulary = searchResult.get();
		}
		
		return favoriteService.addFavorite(vocabulary, customer);
	}

	@PostMapping("/removeFavorite")
	@ResponseBody
	public boolean removeFavorite(@RequestParam String entry) {
		Customer customer = sessionService.getCurrentCustomer();
		Optional<Vocabulary> searchResult = vocabularyService.findVocabulary(entry);
		
		if(!searchResult.isPresent()) {
			return favoriteService.removeFavorite(searchResult.get(), customer);
		}else {
			return false;
		}
		
	}
	
	@GetMapping("/getFavorites")
	@ResponseBody
	public Set<FavoriteEntry> getFavorites() {
		Customer customer = sessionService.getCurrentCustomer();
		
		return favoriteService.getFavorites(customer);
	}
	
	public void setFavoriteService(FavoriteService favoriteService) {
		this.favoriteService = favoriteService;
	}

	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

}
