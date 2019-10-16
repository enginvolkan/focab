package com.engin.focab.services;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.Example;
import com.engin.focab.jpa.FavoriteEntry;
import com.engin.focab.jpa.SearchResult;
import com.engin.focab.jpa.Vocabulary;
@Component
public interface FavoriteService {

	public boolean addFavorite(Vocabulary vocabulary, Customer customer);
	public boolean removeFavorite(Vocabulary vocabulary, Customer customer);
	public Set<FavoriteEntry> getFavorites(Customer customer);
	public Example getARandomExample(Customer customer) throws IOException, InterruptedException;
}
