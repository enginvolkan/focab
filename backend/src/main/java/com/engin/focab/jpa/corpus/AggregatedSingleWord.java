package com.engin.focab.jpa.corpus;

import java.util.List;

public class AggregatedSingleWord {
	private List<String> singleWordSentences;
	private String singleWord;

	public AggregatedSingleWord(String singleWord) {
		super();
		this.singleWord = singleWord;
	}

	public List<String> getSingleWordSentences() {
		return singleWordSentences;
	}

	public void setSingleWordSentences(List<String> singleWordSentences) {
		this.singleWordSentences = singleWordSentences;
	}

	public String getSingleWord() {
		return singleWord;
	}

}
