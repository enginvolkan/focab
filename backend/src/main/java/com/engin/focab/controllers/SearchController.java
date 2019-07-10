package com.engin.focab.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.engin.focab.services.SearchService;

@RestController
public class SearchController {

	private SearchService searchService;
	
	@GetMapping("/search")
	@ResponseBody
	public List<String> search() {
		
		return Arrays.asList("to cut to the chase");
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}
}
