package com.engin.focab.services;

import java.util.HashSet;
import java.util.List;


public interface IndexedSearchService {

	HashSet<String> findIdiomsByWord(String word);

	HashSet<String> findPhrasalsByWord(String word);

	List<String> findCommonWordsByLevel(int level);

}
