package com.engin.focab.services.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.engin.focab.jpa.corpus.PhrasalVerbAnalysis;
import com.engin.focab.services.IndexedSearchService;

import edu.stanford.nlp.simple.Sentence;

@Component
public class PhrasalVerbsDetectionService {
	@Autowired
	private IndexedSearchService indexedSearchService;
	@Autowired
	private SentenceTaggingService sentenceTaggingService;
	@Value("${phrasal.detector.gapUnit}")
	private int gapUnit;

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private StringBuilder trace = new StringBuilder();

	public PhrasalVerbAnalysis detectPhrasalVerbs(String[] taggedSentence, Sentence sentence) {

		HashSet<String> currentSet;
		HashSet<String> foundSet = new HashSet<String>();

		log("Detect phrasal verbs for: " + taggedSentence.toString(), null);
		log("###################################", null);

		for (int i = 0; i < taggedSentence.length; i++) {
			if (sentenceTaggingService.extractTag(taggedSentence[i], false).equals("VB")) {
				// find possible phrasal verbs
				// check the next word
				log("working on " + taggedSentence[i], null);
				if (i + 1 < taggedSentence.length
						&& (!sentenceTaggingService.extractTag(taggedSentence[i + 1], false).equals(".")
								&& !sentenceTaggingService.extractTag(taggedSentence[i + 1], false).equals(","))) {
					String searchTerm = sentenceTaggingService.extractWord(taggedSentence[i]) + " "
							+ sentenceTaggingService.extractWord(taggedSentence[i + 1]);
					log("search term: " + searchTerm, null);

					currentSet = searchWordInPhrasalRepository(searchTerm);
					log("current set : ", currentSet);

					if (currentSet.isEmpty()) {
						log("current set empty, searching for gaps...", null);
						currentSet.addAll(searchPhrasalWithGaps(i, taggedSentence));
					}
					if (currentSet.size() > 1) {
						log("current set is too big, reducing...", null);
						currentSet = reduceCurrentSet(taggedSentence, currentSet, i, searchTerm);
					}

					if (currentSet.size() == 1) {
						foundSet.addAll(currentSet);
					}
				}
			}
		}

		return new PhrasalVerbAnalysis(foundSet, trace.toString());
	}

	private HashSet<String> reduceCurrentSet(String[] taggedSentence, HashSet<String> currentSet, int i,
			String searchTerm) {
		String shortestOne = "";
		int minLength = 0;
		for (Iterator<String> iterator = currentSet.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			if (minLength == 0) {
				minLength = string.split(" ").length;
				shortestOne = string;
			} else {
				if (string.length() < minLength) {
					minLength = string.split(" ").length;
					shortestOne = string;
				}
			}
		}
		log("shortest phrasal verb is :" + shortestOne, null);

		while (currentSet.size() > 1 && (!sentenceTaggingService.extractTag(taggedSentence[i + 2], false).equals(".")
				&& !sentenceTaggingService.extractTag(taggedSentence[i + 2], false).equals(","))) {
			searchTerm = searchTerm + " " + sentenceTaggingService.extractWord(taggedSentence[i + 2]);
			log("search term: " + searchTerm, null);
			currentSet = searchWordInPhrasalRepository(searchTerm);
			log("current set : ", currentSet);

		}
		if (currentSet.isEmpty() && !shortestOne.equals("")) {
			currentSet.add(shortestOne);
		}
		return currentSet;
	}

	// Couldn't find anything on i+1, will search with i+1 +2 etc...
	private HashSet<String> searchPhrasalWithGaps(int i, String[] taggedSentence) {
		for (int j = 1; j <= gapUnit && i + j < taggedSentence.length; j++) {
			String searchTerm = sentenceTaggingService.extractWord(taggedSentence[i]) + " "
					+ sentenceTaggingService.extractWord(taggedSentence[i + j]);
			log("search term: " + searchTerm, null);

			HashSet<String> currentSet = searchWordInPhrasalRepository(searchTerm);
			log("current set : ", currentSet);

			if (!currentSet.isEmpty()) {
				return currentSet;
			}
		}
		return new HashSet<String>();
	}

	private HashSet<String> searchWordInPhrasalRepository(String word) {
		return indexedSearchService.findPhrasalsByWord(word);
	}

	private void log(String message, HashSet<String> set) {
		if (set != null) {
			String s = set.stream().collect(Collectors.joining(","));
			trace.append(message + s);
			trace.append(System.lineSeparator());
			logger.debug(message + s);
		} else {
			trace.append(message);
			trace.append(System.lineSeparator());
			logger.debug(message);
		}
	}

}
