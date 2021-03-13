package com.engin.focab.services.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.FavoriteEntry;
import com.engin.focab.jpa.FavoriteList;
import com.engin.focab.jpa.corpus.ExampleModel;
import com.engin.focab.jpa.corpus.LexiModel;
import com.engin.focab.repository.CustomerRepository;
import com.engin.focab.repository.ExampleRepository;
import com.engin.focab.repository.FavoriteListRepository;
import com.engin.focab.repository.LexiRepository;
import com.engin.focab.services.FavoriteService;
import com.engin.focab.services.LexiDetailsService;
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
	LexiRepository lexiRepository;
	@Autowired
	ScrapperService scrapperService;
	@Autowired
	LexiDetailsService lexiDetailsService;

	@Override
	public boolean addFavorite(LexiModel lexiModel, Customer customer) {
		FavoriteList favoriteList;
		if (!customer.getFavorites().iterator().hasNext()) {
			favoriteList = new FavoriteList("default", customer);
			favoriteListRepository.save(favoriteList);
			customer.getFavorites().add(favoriteList);
			customerRepository.save(customer);
		} else {
			favoriteList = customer.getFavorites().iterator().next();
		}
		FavoriteEntry favoriteEntry = new FavoriteEntry(lexiModel);
		Set<FavoriteEntry> favoriteEntries = favoriteList.getFavoriteEntries();
		favoriteEntries.add(favoriteEntry);
		favoriteList.setFavoriteEntries(favoriteEntries);
		favoriteListRepository.save(favoriteList);

		return true;
	}

	@Override
	public boolean removeFavorite(LexiModel lexiModel, Customer customer) {
		FavoriteList favoriteList = customer.getFavorites().iterator().next();
		return favoriteList.getFavoriteEntries().remove(new FavoriteEntry(lexiModel));
	}

	@Override
	public Set<FavoriteEntry> getFavorites(Customer customer) {
		FavoriteList favoriteList = favoriteListRepository.findFavoriteListByCustomer(customer.getUsername(),
				"default");
		return favoriteList.getFavoriteEntries();
	}

	@Override
	public ExampleModel getARandomExample(Customer customer) throws IOException, InterruptedException {
		FavoriteEntry[] favoriteEntries = favoriteListRepository
				.findFavoriteListByCustomer(customer.getUsername(), "default")
				.getFavoriteEntries().stream().toArray(n -> new FavoriteEntry[n]);
		int size = favoriteEntries.length;
		if (size == 0) {
			return null;
		}
		int item = new Random().nextInt(size);
		LexiModel lexiModel = favoriteEntries[item].getVocabulary();
		ExampleModel[] examples = fetchExamples(lexiModel);
		size = examples.length;
		if (size > 0) {
			item = new Random().nextInt(size);
			return examples[item];
		} else {
			return new ExampleModel();
		}
	}

	private ExampleModel[] fetchExamples(LexiModel lexiModel) throws IOException, InterruptedException {

		ExampleModel[] examples = scrapperService.getExamples(lexiModel);
		List<ExampleModel> newExamples = Arrays.asList(lexiDetailsService.getExamples(lexiModel));

		for (ExampleModel exampleModel : examples) {
			if (exampleModel.getText().toLowerCase().indexOf(lexiModel.getText().replaceAll("/+/", " ")) != -1) {
				exampleModel.setVocabulary(lexiModel);
				exampleRepository.save(exampleModel);
				newExamples.add(exampleModel);
			}
		}
//		vocabularyModel.setExamples(newExamples);
//		vocabularyRepository.save(vocabularyModel);
//		return (ExampleModel[]) vocabularyModel.getExamples().stream().toArray(n -> new ExampleModel[n]);
		return (ExampleModel[]) newExamples.toArray();
	}

}
