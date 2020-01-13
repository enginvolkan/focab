package com.engin.focab.services.impl;

import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

@Component
public class SentenceTaggingService {

	// The statement below works but I found another way and deactivated it
	// private Resource res = new
	// ClassPathResource("english-left3words-distsim.tagger");

	private MaxentTagger tagger;
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired // necessary for @Value to work
	public SentenceTaggingService() {
		try {
//			logger.info("Stanford Model Path: " + res.getFile().getPath());
//			this.tagger = new MaxentTagger(res.getFile().getPath());
			this.tagger = new MaxentTagger("english-left3words-distsim.tagger");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String[] tagString(String text) {
		return this.tagger.tagString(text).split("\\s|-");
	}

	public List<String> lemmas(Sentence sentence) {
		Properties props = new Properties();
		props.setProperty("pos.model", "english-left3words-distsim.tagger");
		List<String> lemmasList = sentence.lemmas(props);
		// remove all punctuations other than @ or #. Those are special chars for idioms
		lemmasList = lemmasList.stream()
				.filter(x -> !Pattern.matches("\\p{Punct}", x) || x.contains("@") || x.contains("#"))
				.collect(Collectors.toList());
		return lemmasList;
	}

	public List<String> posTags(Sentence sentence) {
		Properties props = new Properties();
		props.setProperty("pos.model", "english-left3words-distsim.tagger");
		return sentence.posTags(props);
	}

	public String extractTag(String s, boolean full) {
		String tag = s.substring(s.indexOf('_') + 1, s.length());

		if (full) {
			return tag;

		} else {
			if (tag.length() > 2) {
				return tag.substring(0, 3);
			} else {
				return tag;
			}
		}
	}

	public String extractWord(String s) {
		if (s.indexOf('_') >= 0) {
			return s.substring(0, s.indexOf('_'));
		} else
			return s;
	}

	public String[] tagString(Sentence sentence) {
		return posTags(sentence).toArray(new String[0]);
	}

}
