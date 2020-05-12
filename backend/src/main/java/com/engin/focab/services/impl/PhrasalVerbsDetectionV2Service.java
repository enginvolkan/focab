package com.engin.focab.services.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.engin.focab.services.IndexedSearchService;
import com.engin.focab.services.PhrasalVerbsDetectionService;

import edu.stanford.nlp.simple.Sentence;

@Component("constituencyParser")
public class PhrasalVerbsDetectionV2Service implements PhrasalVerbsDetectionService {
	@Autowired
	private IndexedSearchService indexedSearchService;
	@Autowired
	@Qualifier("phrasalVerbTagRules")
	private LinkedHashMap<String, String> phrasalVerbTagRules;
	@Autowired
	@Qualifier("phrasalVerbOmitTags")
	private List<String> phrasalVerbOmitTags;
	@Autowired
	@Qualifier("phrasalVerbFinalTags")
	private List<String> phrasalVerbFinalTags;

	private final Logger logger = LoggerFactory.getLogger(PhrasalVerbsDetectionV2Service.class);
	private StringBuilder trace = new StringBuilder();

	@Override
	public List<String> detectPhrasalVerbs(Sentence sentence) {
//		props.setProperty("parse.model", "englishPCFG.ser.gz");
//		Tree tree = sentence.parse(props);

		List<String> words = sentence.words();
		Properties props = new Properties();
		props.setProperty("pos.model", "english-left3words-distsim.tagger");
		List<String> lemmas = sentence.lemmas(props);
		List<String> tags = sentence.posTags();
		List<InnerPhrasalVerb> candidates = new LinkedList<InnerPhrasalVerb>();
		List<String> found = new ArrayList<String>();
		int verbIndex = 0;

		for (int i = 0; i < tags.size(); i++) {
			if (verbIndex == 0) {
				// check if it is a verb
				if (tags.get(i).startsWith("VB")) {
					candidates = findCandidates(lemmas.get(i));

					if (!candidates.isEmpty()) {
						// validate candidates for the verb in focus
						for (InnerPhrasalVerb phrasalVerb : new LinkedList<InnerPhrasalVerb>(candidates)) {
							int[] matchIndex = new int[phrasalVerb.words.length];
							StringBuilder gaps[] = new StringBuilder[phrasalVerb.words.length - 1];
							matchIndex[0] = verbIndex;
							int lastMatch = 0;

							for (int j = i + 1; j < words.size(); j++) {
								// loop for the rest of the words and record matched words
								if (phrasalVerb.words[lastMatch + 1].equals(lemmas.get(j))) {
									matchIndex[lastMatch + 1] = j;
									lastMatch++;
									if (lastMatch + 1 == phrasalVerb.words.length) {
										// all words of the phrasal verb exists in the sentence

										// is there a gap?
										if (gaps[0] != null) {
											if (isGapValid(gaps)) {
												found.add(phrasalVerb.fullText);
											} else {
												// phrasal verb is not a match, nothing to do
											}
										} else {
											// no gap but full match
											found.add(phrasalVerb.fullText);
										}
										break;
									}
								} else {
									if (gaps[lastMatch] == null) {
										gaps[lastMatch] = new StringBuilder(" ");
									}
									String shortTag = (tags.get(j).startsWith("VB") || tags.get(j).startsWith("NN")
											|| tags.get(j).startsWith("JJ")) ? tags.get(j).substring(0, 2)
													: tags.get(j);
									gaps[lastMatch] = gaps[lastMatch].append(shortTag + " ");

								}
							}
						}
					}
				}
			}
		}
		return found;
	}

	private class InnerPhrasalVerb {
		String fullText;
		String[] words;
		int numberOfWords;

		InnerPhrasalVerb(String fullText) {
			this.fullText = fullText;
			this.words = fullText.split(" ");
			this.numberOfWords = words.length;

		}

		@Override
		public String toString() {
			return fullText;
		}

	}

	private List<InnerPhrasalVerb> findCandidates(String lemma) {
		List<InnerPhrasalVerb> phrasalVerbs = new ArrayList<>();
		Set<String> candidates = indexedSearchService.findPhrasalsByWord(lemma);
		if (candidates != null) {
			for (String candidate : candidates) {
				phrasalVerbs.add(new InnerPhrasalVerb(candidate));
			}
		}
		return phrasalVerbs;
	}

	private boolean isGapValid(StringBuilder[] gaps) {
		// just focus on gap 1 for now
		StringBuilder remainingTags = gaps[0];
		for (String tag : phrasalVerbOmitTags) {
			remainingTags = replaceSubText(remainingTags, tag, " ");
		}

		ArrayList<String> keys = new ArrayList<>(phrasalVerbTagRules.keySet());

		for (int i = 0; i < keys.size(); i++) {
			String old = remainingTags.toString();
			replaceSubText(remainingTags, keys.get(i), " " + phrasalVerbTagRules.get(keys.get(i)));
			if (!remainingTags.toString().equals(old)) {
				i = -1;
			}
		}

		boolean flag = false;
		for (String finalTagGroup : phrasalVerbFinalTags) {
			if (remainingTags.toString().trim().equals(finalTagGroup)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	private StringBuilder replaceSubText(StringBuilder sentence, String tag, String replace) {
		tag = " " + tag;
		int index = -1;
		do {
			index = sentence.indexOf(tag);
			if (index != -1) {
				sentence.replace(index, index + tag.length() + 1, replace);
			}
		} while (index != -1);
		return sentence;
	}

}
