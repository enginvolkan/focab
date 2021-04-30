package com.engin.focab.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engin.focab.jpa.FormattedSearchResult;
import com.engin.focab.jpa.SearchResult;
import com.engin.focab.jpa.corpus.DefinitionModel;
import com.engin.focab.jpa.corpus.DictionaryDefinition;
import com.engin.focab.jpa.corpus.LexiModel;
import com.engin.focab.repository.LexiRepository;
import com.engin.focab.services.DictionaryService;
import com.engin.focab.services.SearchService;

@Service
public class DefaultSearchService implements SearchService {

	@Autowired
	DictionaryService dictionaryService;

	@Autowired
	LexiRepository lexiRepository;

	@Override
	public List<FormattedSearchResult> getSearchResults(String text) {
		List<FormattedSearchResult> formattedSearchResults = new ArrayList<FormattedSearchResult>();

		LexiModel lexi = lexiRepository.findLexiByText(text);
		if (lexi != null && CollectionUtils.isNotEmpty(lexi.getDefinitions())) {
			for (DefinitionModel definition : lexi.getDefinitions()) {
				FormattedSearchResult searchResult = new FormattedSearchResult(text);
				searchResult.setDefs(List.of(new DictionaryDefinition(definition.getDefinition())));
				formattedSearchResults.add(searchResult);
			}
			return formattedSearchResults;
		}

		List<SearchResult> dictionarySearchResults = dictionaryService.search(text);
		if (dictionarySearchResults != null) {
			for (Iterator iterator = dictionarySearchResults.iterator(); iterator.hasNext();) {
				SearchResult searchResult = (SearchResult) iterator.next();
				FormattedSearchResult formattedSearchResult = new FormattedSearchResult(searchResult.getWord(),
						searchResult.getScore());

				List<DictionaryDefinition> definitions = new ArrayList<DictionaryDefinition>();
				if (searchResult.getDefs() != null) {
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
