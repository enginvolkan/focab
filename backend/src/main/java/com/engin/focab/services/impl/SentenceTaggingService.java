package com.engin.focab.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

@Component
public class SentenceTaggingService {

	@Value("classpath:english-left3words-distsim.tagger")
	private Resource res;
	private MaxentTagger tagger;

	public SentenceTaggingService() {
		// this.tagger = new MaxentTagger(res.getURI().getPath());
		this.tagger = new MaxentTagger(
				"/home/engin/Downloads/stanford-postagger-full-2018-10-16/models/wsj-0-18-left3words-nodistsim.tagger");
	}

	public String[] tagString(String text) {
		return this.tagger.tagString(text).split("\\s|-");
	}

}
