package com.engin.focab.services.impl;

import java.util.HashSet;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.engin.focab.jpa.Customer;
import com.engin.focab.repository.CommonWordRepository;
import com.engin.focab.services.SessionService;

@Component
public class SingleWordDetectionService {
	@Autowired
	private SessionService sessionService;
	@Autowired
	private SentenceTaggingService sentenceTaggingService;
	@Autowired
	private CommonWordRepository commonWordRepository;

	private static HashSet<String> commonWords;

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	public SingleWordDetectionService() {
		super();
		commonWords.addAll(commonWordRepository.findAll().stream().map(x -> x.getWord()).collect(Collectors.toList()));
	}

	public HashSet<String> getKnownWords(){
		Customer customer = sessionService.getCurrentCustomer();
		return customer.

	}

}
