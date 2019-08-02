package com.engin.focab.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.engin.focab.jpa.SearchResult;
import com.engin.focab.jpa.Vocabulary;

public interface SearchService {

	public List<SearchResult> getSearchResults(String text);

	public SearchResult getDefinition(String text);
}
