package com.engin.focab.services.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.engin.focab.jpa.corpus.IdiomAnalysis;
import com.engin.focab.services.IndexedSearchService;

import edu.stanford.nlp.simple.Sentence;

@Component
public class IdiomDetectionService {

	@Autowired
	private IndexedSearchService indexedSearchService;

	private static final Set<String> WORDS_TO_SKIP = Set.of("the", "a", "in", "of", "to", "on", "be", "and", "out",
			"have", "for", "it", "not", "at", "or", "you");
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private final Pattern punctuationPattern = Pattern.compile(".*\\p{Punct}.*");

	public IdiomAnalysis detectIdioms(Sentence sentence) {
		HashSet<String> foundSet = new HashSet<String>();
		Properties props = new Properties();
		props.setProperty("pos.model", "english-left3words-distsim.tagger");
		List<String> sentenceLemmas = sentence.lemmas(props);
		String sentenceInLemmas = String.join(" ", sentenceLemmas);

		// Loop for each word and find the candidate idioms, keep them in a map with
		// associated word index
		HashMap<String, String> idioms = new HashMap<>();
		StringBuilder lemmaString = new StringBuilder();
		for (int i = 0; i < sentenceLemmas.size(); i++) {
			String lemma = sentenceLemmas.get(i);
			Matcher m = punctuationPattern.matcher(lemma);
			if (!m.matches() && !WORDS_TO_SKIP.contains(lemma)) {
				lemmaString.append("|" + lemma);
			}
		}
		if (lemmaString.length() > 1) {
			idioms.putAll(indexedSearchService.findIdiomsByWordWithRegex(lemmaString.toString().substring(1)));
			idioms.forEach((k, v) -> {
				if (sentenceInLemmas.matches(v)) {
					foundSet.add(k);
				}
			});
		}

		return new IdiomAnalysis(foundSet, "");
	}
}
