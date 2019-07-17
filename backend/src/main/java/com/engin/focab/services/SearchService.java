package com.engin.focab.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.engin.focab.jpa.Vocabulary;
@Component
public interface SearchService {

	public List<Vocabulary> getSearchResults(String text);
}