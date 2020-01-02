package com.engin.focab.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.engin.focab.services.IndexedSearchService;

import edu.stanford.nlp.simple.Sentence;

@Component
public class IdiomDetectionService {
	@Autowired
	private IndexedSearchService indexedSearchService;
	@Autowired
	private SentenceTaggingService sentenceTaggingService;
	@Value("${idiom.detector.gapUnit}")
	private int gapUnit;

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	public Set<String> detectIdioms(String[] taggedSentence, Sentence sentence) {

		HashSet<String> trackedWords = new HashSet<String>();
		HashSet<String> previousSet = new HashSet<String>();
		HashSet<String> currentSet = new HashSet<String>();
		HashSet<String> foundSet = new HashSet<String>();

		log("Detect idioms for: " + taggedSentence.toString(), null);
		log("###################################", null);
		for (int i = 0; i < taggedSentence.length; i++) {
			String word = taggedSentence[i];
			log("### Processing " + word, null);

			if (!sentenceTaggingService.extractTag(word, false).equals("PRP")
					&& !sentenceTaggingService.extractTag(word, false).equals("DT")
					&& !sentenceTaggingService.extractTag(word, false).equals("TO")
					&& !sentenceTaggingService.extractTag(word, false).equals("POS")
					&& !sentenceTaggingService.extractTag(word, false).equals(".")) {
				HashSet<String> idioms = searchWordInIdiomRepository(sentenceTaggingService.extractWord(word));

				currentSet.retainAll(idioms);
				log("@ Current set: ", currentSet);

				if (currentSet.isEmpty()) {

					if (!previousSet.isEmpty()) {
						foundSet.addAll(findValidIdioms(trackedWords, sentence));
					}
					trackedWords.clear();
					trackedWords.add(word);

				} else {
					trackedWords.add(word);
				}
				if (currentSet.isEmpty() && i < taggedSentence.length - 1) {
					currentSet.addAll(idioms);
				} else {

				}
				previousSet.clear();
				previousSet.addAll(currentSet);

			}
			log("skipped...", null);

		}
		// Sentence is over, check for valid idioms once more
		if (!currentSet.isEmpty()) {
			foundSet.addAll(findValidIdioms(trackedWords, sentence));
		}

		return foundSet;
	}

	private HashSet<String> findValidIdioms(HashSet<String> trackedWords, Sentence sentence) {

		HashSet<String> foundSet = new HashSet<String>();
		String idiomText = trackedWords.stream().map(x -> sentenceTaggingService.extractWord(x))
				.collect(Collectors.joining(" "));

		if (!idiomText.equals("")) {
			HashSet<String> idiomSet = searchWordInIdiomRepository(idiomText);

			List<String> sentenceLemmas = sentenceTaggingService.lemmas(sentence);

			for (String idiom : idiomSet) {
				if (isIdiomValidForSentence(idiom, sentenceLemmas)) {
					foundSet.add(idiom);
				}
			}
		}
		return foundSet;

	}

	private boolean isIdiomValidForSentence(String idiom, List<String> sentenceLemmas) {

		Sentence idiomSentence = new Sentence((String) idiom.toLowerCase());
		List<String> idiomLemmas = sentenceTaggingService.lemmas(idiomSentence);
		;
		int lastSeen = -1;
		int gap = 0;
		int counter = 0;
		int expectedGap = 0;
		for (String idiomLemma : idiomLemmas) {
			if (!idiomLemma.contains("@") && !idiomLemma.contains("#")) { // no gap
				int firstIndex = sentenceLemmas.indexOf(idiomLemma);
				if (firstIndex > -1 && firstIndex > lastSeen) {

					gap = lastSeen > -1 ? firstIndex - lastSeen - 1 : 0;

					if (gap > expectedGap * gapUnit || gap < expectedGap) {
						break;
					}
					expectedGap = 0;
					lastSeen = firstIndex;
				} else {
					break;
				}
			} else { // expect gap
				expectedGap++;
			}
			counter++;
		}
		return counter == idiomLemmas.size() ? true : false;
	}

	private HashSet<String> searchWordInIdiomRepository(String word) {
		return indexedSearchService.findIdiomsByWord(word);
	}

	private void log(String message, HashSet<String> set) {
		if (set != null) {
			logger.debug(message + set.stream().collect(Collectors.joining(",")));
		} else {
			logger.debug(message);
		}
	}

}
