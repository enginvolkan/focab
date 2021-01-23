package com.engin.focab.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.engin.focab.jpa.FormattedSearchResult;
import com.engin.focab.jpa.SearchResult;
import com.engin.focab.jpa.corpus.LexiModel;

public interface SearchService {

	public List<FormattedSearchResult> getSearchResults(String text);

	public FormattedSearchResult getDefinition(String text);
}
