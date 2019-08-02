package com.engin.focab.services;

import java.util.List;

import com.engin.focab.jpa.SearchResult;

public interface DictionaryService {
	public List<SearchResult> search(String s);
	public SearchResult define(String s);
}
