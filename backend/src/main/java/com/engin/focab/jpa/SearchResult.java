package com.engin.focab.jpa;

//********************************************
//Generated by http://www.jsonschema2pojo.org/
//********************************************

	import java.util.List;
	import com.google.gson.annotations.Expose;
	import com.google.gson.annotations.SerializedName;

	public class SearchResult {

	@SerializedName("word")
	@Expose
	private String word;
	@SerializedName("score")
	@Expose
	private int score;
	@SerializedName("tags")
	@Expose
	private List<String> tags = null;
	@SerializedName("defs")
	@Expose
	private List<String> defs = null;

	/**
	* No args constructor for use in serialization
	* 
	*/
	public SearchResult() {
	}

	/**
	* 
	* @param tags
	* @param defs
	* @param score
	* @param word
	*/
	public SearchResult(String word, int score, List<String> tags, List<String> defs) {
	super();
	this.word = word;
	this.score = score;
	this.tags = tags;
	this.defs = defs;
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

	public List<String> getTags() {
	return tags;
	}

	public void setTags(List<String> tags) {
	this.tags = tags;
	}

	public List<String> getDefs() {
	return defs;
	}

	public void setDefs(List<String> defs) {
	this.defs = defs;
	}

	
}
