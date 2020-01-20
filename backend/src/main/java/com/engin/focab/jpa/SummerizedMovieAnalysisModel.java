package com.engin.focab.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.engin.focab.jpa.corpus.AggregatedIdiom;
import com.engin.focab.jpa.corpus.AggregatedPhrasalVerb;
import com.engin.focab.jpa.corpus.AggregatedSingleWord;

@Entity
@Table(name = "movieanalysis")

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

	private String imdbId;

	private List<AggregatedIdiom> idioms = new ArrayList<AggregatedIdiom>();

	private List<AggregatedPhrasalVerb> phrasalVerbs = new ArrayList<AggregatedPhrasalVerb>();

	private List<AggregatedSingleWord> singleWords = new ArrayList<AggregatedSingleWord>();

	/////////////////////
	// Getters and Setters
	/////////////////////

	public List<AggregatedIdiom> getIdioms() {
		return idioms;
	}

	public void setIdioms(List<AggregatedIdiom> idioms) {
		this.idioms = idioms;
	}

	public List<AggregatedPhrasalVerb> getPhrasalVerbs() {
		return phrasalVerbs;
	}

	public void setPhrasalVerbs(List<AggregatedPhrasalVerb> phrasalVerbs) {
		this.phrasalVerbs = phrasalVerbs;
	}

	public List<AggregatedSingleWord> getSingleWords() {
		return singleWords;
	}

	public void setSingleWords(List<AggregatedSingleWord> singleWords) {
		this.singleWords = singleWords;
	}

	public String getImdbId() {
		return imdbId;
	}
}
