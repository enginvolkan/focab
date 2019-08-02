package com.engin.focab.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.SearchResult;
import com.engin.focab.jpa.Vocabulary;
@Component
public interface FavoriteService {

	public boolean addFavorite(Vocabulary vocabulary, Customer customer);
	public boolean removeFavorite(Vocabulary vocabulary, Customer customer);
}
