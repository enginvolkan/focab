package com.engin.focab.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.corpus.LexiModel;
import com.engin.focab.services.FavoriteService;
import com.engin.focab.services.LexiDetailsService;
import com.engin.focab.services.SessionService;

//@CrossOrigin(origins = "http://localhost:4200", allowCredentials="true")
@RestController
public class FavoriteController {

	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private LexiDetailsService lexiDetailsService;
	@Autowired
	private SessionService sessionService;


	@PostMapping("/addFavorite")
	@ResponseBody
	public boolean addFavorite(@RequestParam String entry) {
		Customer customer = sessionService.getCurrentCustomer();
		Optional<LexiModel> searchResult = lexiDetailsService.findLexi(entry);
		LexiModel lexiModel;

		if(!searchResult.isPresent()) {
			lexiModel = lexiDetailsService.createVocabulary(entry);
		}else {
			lexiModel = searchResult.get();
		}

		return favoriteService.addFavorite(lexiModel, customer);
	}

	@PostMapping("/removeFavorite")
	@ResponseBody
	public boolean removeFavorite(@RequestParam String entry) {
		Customer customer = sessionService.getCurrentCustomer();
		Optional<LexiModel> searchResult = lexiDetailsService.findLexi(entry);

		if (searchResult.isPresent()) {
			return favoriteService.removeFavorite(searchResult.get(), customer);
		}else {
			return false;
		}

	}

	@GetMapping("/getFavorites")
	@ResponseBody
	public List<String> getFavorites() {
		Customer customer = sessionService.getCurrentCustomer();

		return favoriteService.getFavorites(customer).stream().map(x -> x.getVocabulary().getText())
				.collect(Collectors.toList());
	}

	public void setFavoriteService(FavoriteService favoriteService) {
		this.favoriteService = favoriteService;
	}

	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

}
