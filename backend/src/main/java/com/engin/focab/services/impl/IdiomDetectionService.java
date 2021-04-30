package com.engin.focab.services.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.engin.focab.constants.FocabConstants;
import com.engin.focab.jpa.IdiomAnalysis;
import com.engin.focab.services.IndexedSearchService;

import edu.stanford.nlp.simple.Sentence;

@Component
public class IdiomDetectionService {

	@Autowired
	private IndexedSearchService indexedSearchService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	public IdiomAnalysis detectIdioms(Sentence sentence, List<String> sentenceLemmas) {
		HashSet<String> foundSet = new HashSet<>();

		String sentenceInLemmas = String.join(" ", sentenceLemmas);

		// Loop for each word and find the candidate idioms, keep them in a map with
		// associated word index

		// idiom and its respective regex
		HashMap<String, String> idioms = new HashMap<>();
		StringBuilder lemmaString = new StringBuilder();
		for (int i = 0; i < sentenceLemmas.size(); i++) {
			String lemma = sentenceLemmas.get(i);
			Matcher m = Pattern.compile(FocabConstants.WORD_REGEX_PATTERN).matcher(lemma);
			if (m.matches() && !FocabConstants.WORDS_TO_SKIP.contains(lemma)) {
				lemmaString.append("|" + lemma);
			}
		}
		if (lemmaString.length() > 1) {
			// Find a larger set for all possible idioms
			idioms.putAll(indexedSearchService.findIdiomsByWordWithRegex(lemmaString.toString().substring(1)));

			// eliminate non matching idioms
			idioms.forEach((k, v) -> {
				if (sentenceInLemmas.matches(v)) {
					foundSet.add(k);
				}
			});
		}

		return new IdiomAnalysis(foundSet, "");
	}
}
