package com.engin.focab.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.engin.focab.jpa.Customer;
import com.engin.focab.services.IndexedSearchService;
import com.engin.focab.services.SessionService;

@Component
public class SingleWordDetectionService {
	@Autowired
	private SessionService sessionService;
	@Autowired
	private IndexedSearchService indexedSearchService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());


	public List<String> getCommonWords() {

		Customer customer = sessionService.getCurrentCustomer();
		List<String> allCommonWords = indexedSearchService.findCommonWordsByLevel(customer.getLevel());
		return allCommonWords;
	}

	public SingleWordDetectionService() {
		super();
	}

	public List<String> getKnownWords() {
		Customer customer = sessionService.getCurrentCustomer();
		return List.of("engin");
	}


}
