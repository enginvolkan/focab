package com.engin.focab.jpa;

	import java.util.List;

	public class FormattedSearchResult {


	private String word;

	private int score;

	private List<DictionaryDefinition> defs = null;

	public FormattedSearchResult() {
	}

	public FormattedSearchResult(String word, int score) {
	super();
	this.word = word;
	this.score = score;
	}

	public String getWord() {
	return word;
	}

	public void setWord(String word) {
	this.word = word;
	}

	public int getScore() {
	return score;
	}

	public void setScore(int score) {
	this.score = score;
	}


	public List<DictionaryDefinition> getDefs() {
	return defs;
	}

	public void setDefs(List<DictionaryDefinition> defs) {
	this.defs = defs;
	}

	
}
