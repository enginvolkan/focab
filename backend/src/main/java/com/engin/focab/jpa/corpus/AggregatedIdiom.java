package com.engin.focab.jpa.corpus;

import java.util.List;

public class AggregatedIdiom {
	private List<String> idiomSentences;
	private String idiom;

	public AggregatedIdiom(String idiom) {
		super();
		this.idiom = idiom;
	}

	public List<String> getIdiomSentences() {
		return idiomSentences;
	}

	public void setIdiomSentences(List<String> idiomSentences) {
		this.idiomSentences = idiomSentences;
	}

	public String getIdiom() {
		return idiom;
	}

}
