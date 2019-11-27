package com.engin.focab.services;

import java.util.HashSet;

public interface IndexedSearchService {

	HashSet<String> findIdiomsByWord(String word);

}
