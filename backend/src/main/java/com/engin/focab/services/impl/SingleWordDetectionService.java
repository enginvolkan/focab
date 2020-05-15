package com.engin.focab.services.impl;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.engin.focab.jpa.Customer;
import com.engin.focab.services.SessionService;

@Component
public class SingleWordDetectionService {
	@Autowired
	private SessionService sessionService;
	@Autowired
	private List<Set<String>> commonWords;

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());


	public Set<String> getCommonWords() {
		return commonWords.get(0);
	}

	public SingleWordDetectionService() {
		super();
	}

	public List<String> getKnownWords(Customer customer) {

		return List.of("engin");
	}

	private String cleanPunctuation(String s) {
		return s.replaceAll("[^a-zA-Z '-]", " ");
	}

	public Set<String> reduce(Set<String> singleWordSet) {

//		Customer customer = sessionService.getCurrentCustomer();
		singleWordSet.removeAll(getCommonWords());
//		singleWordSet.removeAll(getKnownWords(customer));

		return singleWordSet;

	}
}
