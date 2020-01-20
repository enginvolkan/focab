package com.engin.focab.jpa.corpus;

import java.util.List;

public class AggregatedPhrasalVerb {
	private List<String> phrasalVerbSentences;
	private String phrasalVerb;

	public AggregatedPhrasalVerb(String phrasalVerb) {
		super();
		this.phrasalVerb = phrasalVerb;
	}

	public List<String> getPhrasalVerbSentences() {
		return phrasalVerbSentences;
	}

	public void setPhrasalVerbSentences(List<String> phrasalVerbSentences) {
		this.phrasalVerbSentences = phrasalVerbSentences;
	}

	public String getPhrasalVerb() {
		return phrasalVerb;
	}

}
