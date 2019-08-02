package com.engin.focab.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.FavoriteEntry;
import com.engin.focab.jpa.FavoriteList;
import com.engin.focab.jpa.SearchResult;
import com.engin.focab.jpa.Vocabulary;
import com.engin.focab.repository.FavoriteListRepository;
import com.engin.focab.services.DictionaryService;
import com.engin.focab.services.FavoriteService;
import com.engin.focab.services.SearchService;

@Component
public class DefaultFavoriteService implements FavoriteService {

	@Autowired
	FavoriteListRepository favoriteListRepository;
	
	@Override
	public boolean addFavorite(Vocabulary vocabulary, Customer customer) {
		FavoriteList favoriteList = customer.getFavorites().iterator().next();
		return favoriteList.getFavoriteEntries().add(new FavoriteEntry(vocabulary));
	}

	@Override
	public boolean removeFavorite(Vocabulary vocabulary, Customer customer) {
		FavoriteList favoriteList = customer.getFavorites().iterator().next();
		return favoriteList.getFavoriteEntries().remove(new FavoriteEntry(vocabulary));
	}

}
