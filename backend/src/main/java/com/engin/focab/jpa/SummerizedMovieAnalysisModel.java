package com.engin.focab.jpa;

import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "summerizedanalysis")

public class SummerizedMovieAnalysisModel {

	//////////////
	// Constractors
	//////////////
	public SummerizedMovieAnalysisModel(String string) {
		super();
		this.imdbId = string;
	}

	public SummerizedMovieAnalysisModel() {
		super();
	}

	//////////////
	// Fields
	//////////////
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer pk;

	private String imdbId;

	private HashMap<String, List<String>> idioms = new HashMap<String, List<String>>();

	private HashMap<String, List<String>> phrasalVerbs = new HashMap<String, List<String>>();

	private HashMap<String, List<String>> singleWords = new HashMap<String, List<String>>();

	/////////////////////
	// Getters and Setters
	/////////////////////

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public HashMap<String, List<String>> getIdioms() {
		return idioms;
	}

	public void setIdioms(HashMap<String, List<String>> idioms) {
		this.idioms = idioms;
	}

	public HashMap<String, List<String>> getPhrasalVerbs() {
		return phrasalVerbs;
	}

	public void setPhrasalVerbs(HashMap<String, List<String>> phrasalVerbs) {
		this.phrasalVerbs = phrasalVerbs;
	}

	public HashMap<String, List<String>> getSingleWords() {
		return singleWords;
	}

	public void setSingleWords(HashMap<String, List<String>> singleWords) {
		this.singleWords = singleWords;
	}

}
