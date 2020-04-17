package com.engin.focab.services.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.engin.focab.services.IndexedSearchService;
import com.engin.focab.services.PhrasalVerbsDetectionService;

import edu.stanford.nlp.simple.Sentence;

@Component("manualParser")
public class ManualPhrasalVerbsDetectionService implements PhrasalVerbsDetectionService {
	@Autowired
	private IndexedSearchService indexedSearchService;
	@Autowired
	private SentenceTaggingService sentenceTaggingService;
	@Value("${phrasal.detector.gapUnit}")
	private int gapUnit;

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private StringBuilder trace;

	@Override
	public List<String> detectPhrasalVerbs(Sentence sentence) {

//		Set<String> currentSet;
//		HashSet<String> foundSet = new HashSet<String>();
//
//		trace = new StringBuilder("<br>*****************************************************<br>");
//		log("Detect phrasal verbs for: " + Arrays.toString(taggedSentence), null);
//		log("*****************************************************", null);
//
//		for (int i = 0; i < taggedSentence.length; i++) {
//			if (sentenceTaggingService.extractTag(taggedSentence[i], false).startsWith("VB")) {
//				// find possible phrasal verbs
//				// check the next word
//				log("working on " + taggedSentence[i], null);
//				if (i + 1 < taggedSentence.length
//						&& (!sentenceTaggingService.extractTag(taggedSentence[i + 1], false).equals(".")
//								&& !sentenceTaggingService.extractTag(taggedSentence[i + 1], false).equals(",")
//								&& !sentenceTaggingService.extractTag(taggedSentence[i + 1], false).equals(":"))) {
//					String searchTerm = sentenceTaggingService.extractWord(taggedSentence[i]) + " "
//							+ sentenceTaggingService.extractWord(taggedSentence[i + 1]);
//					log("@search term: " + searchTerm, null);
//
//					currentSet = searchWordInPhrasalRepository(searchTerm);
//					log("@current set : ", currentSet);
//
//					if (currentSet.isEmpty()) {
//						log("@current set empty, searching for gaps...", null);
//						if (!sentenceTaggingService.extractTag(taggedSentence[i + 1], false).startsWith("VB")) {
//							currentSet.addAll(searchPhrasalWithGaps(i, taggedSentence));
//						}
//					}
//					if (currentSet.size() > 1) {
//						log("@current set is too big, reducing...", null);
//						currentSet = reduceCurrentSet(taggedSentence, currentSet, i, searchTerm);
//					}
//
//					if (currentSet.size() == 1) {
//						if (validateCurrentSet(currentSet, searchTerm)) {
//							foundSet.addAll(currentSet);
//						}
//					}
//				} else {
//					log("nothing significant next...", null);
//				}
//			}
//		}
//		log("@@@@@ found:", foundSet);
//		return new PhrasalVerbAnalysis(foundSet, trace.toString());
		return null;
	}

	private boolean validateCurrentSet(Set<String> currentSet, String searchTerm) {
		if (currentSet.size() != 1) {
			throw new IllegalArgumentException("Validation only works for a single found text!");
		}
		Set<String> foundLemmas = new HashSet<String>(
				sentenceTaggingService.lemmas((String) currentSet.iterator().next()));
		Set<String> searchLemmas = new HashSet<String>(sentenceTaggingService.lemmas(searchTerm));

		if (foundLemmas.equals(searchLemmas)) {
			return true;
		}
		return false;
	}

	private Set<String> reduceCurrentSet(String[] taggedSentence, Set<String> currentSet, int i, String searchTerm) {
		String currentOne = "";
		int minLength = 0;
		for (Iterator<String> iterator = currentSet.iterator(); iterator.hasNext();) {
			String string = iterator.next();
			if (minLength == 0) {
				minLength = string.split(" ").length;
				currentOne = string;
			} else {
				if (string.split(" ").length < minLength) {
					minLength = string.split(" ").length;
					currentOne = string;
				}
			}
		}
		log("@@@shortest phrasal verb is :" + currentOne, null);

		for (int j = 1; !currentSet.isEmpty() && i + j < taggedSentence.length; j++) {
			if (!sentenceTaggingService.extractTag(taggedSentence[i + j], false).equals(".")
					&& !sentenceTaggingService.extractTag(taggedSentence[i + j], false).equals(",")) {
				searchTerm = searchTerm + " " + sentenceTaggingService.extractWord(taggedSentence[i + j]);
				log("@@@reducing search term: " + searchTerm, null);
				currentSet = searchWordInPhrasalRepository(searchTerm);
				log("@@@reducing current set : ", currentSet);
				if (currentSet.size() == 1) {
					currentOne = currentSet.iterator().next();
				}
			}
		}

		return new HashSet<String>(Collections.singleton(currentOne));
	}

	// Couldn't find anything on i+1, will search with i+1 +2 etc...
	private Set<String> searchPhrasalWithGaps(int i, String[] taggedSentence) {
		for (int j = 2; j - 1 <= gapUnit && i + j < taggedSentence.length; j++) {
			if (!sentenceTaggingService.extractTag(taggedSentence[i + j], true).equals(".")
					&& !sentenceTaggingService.extractTag(taggedSentence[i + j], true).equals(",")) {
				String searchTerm = sentenceTaggingService.extractWord(taggedSentence[i]) + " "
						+ sentenceTaggingService.extractWord(taggedSentence[i + j]);
				log("@@gap search term: " + searchTerm, null);

				HashSet<String> currentSet = searchWordInPhrasalRepository(searchTerm);
				log("@@gap current set : ", currentSet);

				if (!currentSet.isEmpty()) {
					if (currentSet.size() == 1) {
						if (validateCurrentSet(currentSet, searchTerm)) {
							return currentSet;
						}
					} else {
						return reduceCurrentSet(taggedSentence, currentSet, i + j, searchTerm);
					}
				}
			}
		}
		return new HashSet<String>();
	}

	private HashSet<String> searchWordInPhrasalRepository(String word) {
		return indexedSearchService.findPhrasalsByWord(word);
	}

	private void log(String message, Set<String> set) {
		if (set != null) {
			String s = set.stream().collect(Collectors.joining(","));
			trace.append(message + s);
			trace.append("<br>");
			logger.debug(message + s);
		} else {
			trace.append(message);
			trace.append("<br>");
			logger.debug(message);
		}
	}

}
