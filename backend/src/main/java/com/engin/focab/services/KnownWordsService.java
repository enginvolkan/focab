package com.engin.focab.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.corpus.LexiModel;
@Component
public interface KnownWordsService {

	public boolean addKnownWord(LexiModel lexiModel, Customer customer);

	public boolean removeKnownWord(LexiModel lexiModel, Customer customer);

	public List<String> getKnownWords(Customer customer);
}
