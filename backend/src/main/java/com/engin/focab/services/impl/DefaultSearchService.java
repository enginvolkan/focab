package com.engin.focab.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.engin.focab.jpa.SearchResult;
import com.engin.focab.jpa.Vocabulary;
import com.engin.focab.services.DictionaryService;
import com.engin.focab.services.SearchService;

@Service
public class DefaultSearchService implements SearchService {
	
	@Autowired
	DictionaryService dictionaryService;

	@Override
	public List<SearchResult> getSearchResults(String text) {
		
		return dictionaryService.search(text);
	}

	@Override
	public SearchResult getDefinition(String text) {
		return dictionaryService.define(text);
	}

}
