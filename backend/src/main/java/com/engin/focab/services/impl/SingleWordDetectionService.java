package com.engin.focab.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.corpus.CommonWordModel;
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

	public List<String> getCommonWords(){
		List<CommonWordModel> allCommonWords = commonWordRepository.findAll();
		return allCommonWords.stream().map(x-> x.getWord()).collect(Collectors.toList());
	}
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	public SingleWordDetectionService() {
		super();

	}

	public HashSet<String> getKnownWords(){
		Customer customer = sessionService.getCurrentCustomer();
		return null;

	}


}
