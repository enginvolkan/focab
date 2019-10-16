package com.engin.focab.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.engin.focab.jpa.DictionaryDefinition;
import com.engin.focab.jpa.FormattedSearchResult;
import com.engin.focab.jpa.SearchResult;
import com.engin.focab.jpa.Vocabulary;
import com.engin.focab.services.DictionaryService;
import com.engin.focab.services.SearchService;

@Service
public class DefaultSearchService implements SearchService {
	
	@Autowired
	DictionaryService dictionaryService;

	@Override
	public List<FormattedSearchResult> getSearchResults(String text) {
		List<SearchResult> dictionarySearchResults = dictionaryService.search(text);
		List<FormattedSearchResult> formattedSearchResults = new ArrayList<FormattedSearchResult>();

		for (Iterator iterator = dictionarySearchResults.iterator(); iterator.hasNext();) {
			SearchResult searchResult = (SearchResult) iterator.next();
			FormattedSearchResult formattedSearchResult = new FormattedSearchResult(searchResult.getWord(),searchResult.getScore());
			
			List<DictionaryDefinition> definitions = new ArrayList<DictionaryDefinition>();
			if (searchResult.getDefs()!=null) {
				for (Iterator iterator2 = searchResult.getDefs().iterator(); iterator2.hasNext();) {
					String definitionString = (String) iterator2.next();
					String tag = beautifyTag(definitionString.substring(0, definitionString.indexOf("\t")));
					String definition = definitionString.substring(definitionString.indexOf("\t") + 1,
							definitionString.length());
					definitions.add(new DictionaryDefinition(tag, definition));
				} 
			}
			formattedSearchResult.setDefs(definitions);
			formattedSearchResults.add(formattedSearchResult);
		}
		return formattedSearchResults;
	}
	
	private String beautifyTag(String text) {
		String resutingText;
		switch (text) {
		case "n":
			resutingText="Noun";
			break;
		case "v":
			resutingText="Verb";
			break;
		case "adj":
			resutingText="Adjective";
			break;
		case "adv":
			resutingText="Adverb";
			break;
		default:
			resutingText=text;
			break;
		}
		return resutingText;
	}
	@Override
	public FormattedSearchResult getDefinition(String text) {
		return null;
//		return dictionaryService.define(text);
	}

}
