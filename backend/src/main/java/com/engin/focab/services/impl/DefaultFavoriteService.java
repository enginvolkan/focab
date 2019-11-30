package com.engin.focab.services.impl;

import java.io.IOException;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.Example;
import com.engin.focab.jpa.FavoriteEntry;
import com.engin.focab.jpa.FavoriteList;
import com.engin.focab.jpa.Vocabulary;
import com.engin.focab.repository.CustomerRepository;
import com.engin.focab.repository.ExampleRepository;
import com.engin.focab.repository.FavoriteListRepository;
import com.engin.focab.repository.VocabularyRepository;
import com.engin.focab.services.FavoriteService;
import com.engin.focab.services.ScrapperService;

@Component
public class DefaultFavoriteService implements FavoriteService {

	@Autowired
	FavoriteListRepository favoriteListRepository;
	@Autowired
	ExampleRepository exampleRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	VocabularyRepository vocabularyRepository;
	@Autowired
	ScrapperService scrapperService;

	@Override
	public boolean addFavorite(Vocabulary vocabulary, Customer customer) {
		FavoriteList favoriteList;
		if (!customer.getFavorites().iterator().hasNext()) {
			favoriteList = new FavoriteList("default", customer);
			favoriteListRepository.save(favoriteList);
			customer.getFavorites().add(favoriteList);
			customerRepository.save(customer);
		} else {
			favoriteList = customer.getFavorites().iterator().next();
		}
		FavoriteEntry favoriteEntry = new FavoriteEntry(vocabulary);
		Set<FavoriteEntry> favoriteEntries = favoriteList.getFavoriteEntries();
		favoriteEntries.add(favoriteEntry);
		favoriteList.setFavoriteEntries(favoriteEntries);
		favoriteListRepository.save(favoriteList);

		return true;
	}

	@Override
	public boolean removeFavorite(Vocabulary vocabulary, Customer customer) {
		FavoriteList favoriteList = customer.getFavorites().iterator().next();
		return favoriteList.getFavoriteEntries().remove(new FavoriteEntry(vocabulary));
	}

	@Override
	public Set<FavoriteEntry> getFavorites(Customer customer) {
		FavoriteList favoriteList = favoriteListRepository.findFavoriteListByCustomer(customer.getId(), "default");
		return favoriteList.getFavoriteEntries();
	}

	@Override
	public Example getARandomExample(Customer customer) throws IOException, InterruptedException {
		FavoriteEntry[] favoriteEntries = favoriteListRepository.findFavoriteListByCustomer(customer.getId(), "default")
				.getFavoriteEntries().stream().toArray(n -> new FavoriteEntry[n]);
		int size = favoriteEntries.length;
		if (size == 0) {
			return null;
		}
		int item = new Random().nextInt(size);
		Vocabulary vocabulary = (Vocabulary) favoriteEntries[item].getVocabulary();
		Example[] examples;
		if (vocabulary.getExamples().isEmpty()) {
			examples = fetchExamples(vocabulary);
		} else {
			examples = vocabulary.getExamples().stream().toArray(n -> new Example[n]);
		}
		size = examples.length;
		if (size > 0) {
			item = new Random().nextInt(size);
			return examples[item];
		} else {
			return new Example();
		}
	}

	private Example[] fetchExamples(Vocabulary vocabulary) throws IOException, InterruptedException {

		Example[] examples = scrapperService.getExamples(vocabulary);
		Set<Example> newExamples = vocabulary.getExamples();

		for (Example example : examples) {
			if (example.getText().toLowerCase().indexOf(vocabulary.getText().replaceAll("/+/", " ")) != -1) {
				example.setVocabulary(vocabulary);
				exampleRepository.save(example);
				newExamples.add(example);
			}
		}
		vocabulary.setExamples(newExamples);
		vocabularyRepository.save(vocabulary);
		return (Example[]) vocabulary.getExamples().stream().toArray(n -> new Example[n]);
	}

}
