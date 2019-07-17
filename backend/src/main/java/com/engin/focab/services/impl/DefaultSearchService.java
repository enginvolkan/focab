package com.engin.focab.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.engin.focab.jpa.Vocabulary;
import com.engin.focab.services.SearchService;

@Component
public class DefaultSearchService implements SearchService {

	@Override
	public List<Vocabulary> getSearchResults(String text) {
		// TODO Auto-generated method stub
		return Arrays.asList(new Vocabulary("cut to the chase"));
	}

}
