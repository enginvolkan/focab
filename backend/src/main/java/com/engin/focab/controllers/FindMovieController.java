package com.engin.focab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.omdbapi.OmdbApiEpisodeSearchResult;
import com.engin.focab.jpa.omdbapi.OmdbApiSearchResult;
import com.engin.focab.jpa.omdbapi.OmdbMovieOrSeriesDetailModel;
import com.engin.focab.repository.AuthorityRepository;
import com.engin.focab.repository.CustomerRepository;
import com.engin.focab.services.MovieInfoService;
import com.engin.focab.services.SessionService;

//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/movieInfo")
public class FindMovieController {

	@Autowired
	private MovieInfoService movieInfoService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	AuthorityRepository authorityRepository;

	@GetMapping("/findMovieOrSeries")
	@ResponseBody
	public OmdbApiSearchResult findMovie(@RequestParam String searchText) {
		Customer customer = sessionService.getCurrentCustomer();

		if (customer != null) {
			return movieInfoService.findMovieOrSeries(searchText);
		} else {
			return new OmdbApiSearchResult();
		}
	}

	@GetMapping("/getSeriesEpisodes")
	@ResponseBody
	public OmdbApiEpisodeSearchResult findMovie(@RequestParam String imdbId, @RequestParam int season) {
		Customer customer = sessionService.getCurrentCustomer();

		if (customer != null) {
			return movieInfoService.findEpisode(imdbId, season);
		} else {
			return new OmdbApiEpisodeSearchResult();
		}
	}

	@GetMapping("/getMovieOrSeriesDetails")
	@ResponseBody
	public OmdbMovieOrSeriesDetailModel getDetails(@RequestParam String imdbId) {
		Customer customer = sessionService.getCurrentCustomer();

		if (customer != null) {
			return movieInfoService.getMovieOrEpisodeDetails(imdbId);
		} else {
			return new OmdbMovieOrSeriesDetailModel();
		}
	}

	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

}
