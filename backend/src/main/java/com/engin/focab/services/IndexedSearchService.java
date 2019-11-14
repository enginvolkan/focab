package com.engin.focab.services;

import java.net.MalformedURLException;
import java.util.HashSet;

public interface IndexedSearchService {

	HashSet<String> findIdiomsByWord(String word) throws MalformedURLException;

}
