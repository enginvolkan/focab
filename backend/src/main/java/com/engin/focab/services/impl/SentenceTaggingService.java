package com.engin.focab.services.impl;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

@Component
public class SentenceTaggingService {
	
	@Value("classpath:english-left3words-distsim.tagger")
    private Resource res;
	
	private MaxentTagger tagger;
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	public SentenceTaggingService() {
		try {
			logger.info("Stanford Model Path: " + res.getURI().getRawPath());
			this.tagger = new MaxentTagger(res.getURI().getRawPath());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String[] tagString(String text) {
		return this.tagger.tagString(text).split("\\s|-");
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

}
