package com.engin.focab.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.corpus.LexiModel;
import com.engin.focab.repository.CustomerRepository;
import com.engin.focab.repository.ExampleRepository;
import com.engin.focab.repository.FavoriteListRepository;
import com.engin.focab.repository.LexiRepository;
import com.engin.focab.services.KnownWordsService;
import com.engin.focab.services.LexiDetailsService;
import com.engin.focab.services.ScrapperService;

@Component
public class DefaultKnownWordsService implements KnownWordsService {

	@Autowired
	FavoriteListRepository favoriteListRepository;
	@Autowired
	ExampleRepository exampleRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	LexiRepository lexiRepository;
	@Autowired
	ScrapperService scrapperService;
	@Autowired
	LexiDetailsService lexiDetailsService;

	@Override
	public boolean addKnownWord(LexiModel lexiModel, Customer customer) {
		Set<LexiModel> knownWordsList = customer.getKnownWords();
		if (CollectionUtils.isEmpty(knownWordsList)) {
			knownWordsList = new HashSet<>();
		}
		boolean result = knownWordsList.add(lexiModel);
		if (result) {
			customer.setKnownWords(knownWordsList);
			customerRepository.save(customer);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeKnownWord(LexiModel lexiModel, Customer customer) {
		Set<LexiModel> knownWordsList = customer.getKnownWords();
		if (CollectionUtils.isNotEmpty(knownWordsList)) {
			knownWordsList.removeIf(x -> x.equals(lexiModel));
			customer.setKnownWords(knownWordsList);
			customerRepository.save(customer);
			return true;
		}
		return false;
	}

	@Override
	public List<String> getKnownWords(Customer customer) {
		Set<LexiModel> knownWordsList = customer.getKnownWords();

		if (CollectionUtils.isNotEmpty(knownWordsList)) {
			return knownWordsList.stream().map(x -> x.getText()).collect(Collectors.toList());
		}
		return List.of("");
	}

}
