package com.engin.focab.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.engin.focab.jpa.Vocabulary;
import com.engin.focab.services.SearchService;

@RestController
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@GetMapping("/search")
	@ResponseBody
	public List<Vocabulary> search(@RequestParam String q) {
		return searchService.getSearchResults(q);
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}
}
