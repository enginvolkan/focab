package com.engin.focab.services;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.FavoriteEntry;
import com.engin.focab.jpa.SearchResult;
import com.engin.focab.jpa.corpus.ExampleModel;
import com.engin.focab.jpa.corpus.LexiModel;
@Component
public interface FavoriteService {

	public boolean addFavorite(LexiModel lexiModel, Customer customer);
	public boolean removeFavorite(LexiModel lexiModel, Customer customer);
	public Set<FavoriteEntry> getFavorites(Customer customer);
	public ExampleModel getARandomExample(Customer customer) throws IOException, InterruptedException;
}
