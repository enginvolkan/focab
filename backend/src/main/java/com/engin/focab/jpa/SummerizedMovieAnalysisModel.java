package com.engin.focab.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.engin.focab.dtos.LexiDto;

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

	private ArrayList<LexiDto> idioms = new ArrayList<>();

	private ArrayList<LexiDto> phrasalVerbs = new ArrayList<>();

	private ArrayList<LexiDto> singleWords = new ArrayList<>();

	private long idiomAverageDuration;
	private long phrasalAverageDuration;
	private long singleAverageDuration;
	private long singleMassDuration;

	/////////////////////
	// Getters and Setters
	/////////////////////

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public List<LexiDto> getIdioms() {
		return idioms;
	}

	public void setIdioms(ArrayList<LexiDto> idioms) {
		this.idioms = idioms;
	}

	public List<LexiDto> getPhrasalVerbs() {
		return phrasalVerbs;
	}

	public void setPhrasalVerbs(ArrayList<LexiDto> phrasalVerbs) {
		this.phrasalVerbs = phrasalVerbs;
	}

	public List<LexiDto> getSingleWords() {
		return singleWords;
	}

	public void setSingleWords(ArrayList<LexiDto> singleWords) {
		this.singleWords = singleWords;
	}

	public long getIdiomAverageDuration() {
		return idiomAverageDuration;
	}

	public void setIdiomAverageDuration(long idiomAverageDuration) {
		this.idiomAverageDuration = idiomAverageDuration;
	}

	public long getPhrasalAverageDuration() {
		return phrasalAverageDuration;
	}

	public void setPhrasalAverageDuration(long phrasalAverageDuration) {
		this.phrasalAverageDuration = phrasalAverageDuration;
	}

	public long getSingleAverageDuration() {
		return singleAverageDuration;
	}

	public void setSingleAverageDuration(long singleAverageDuration) {
		this.singleAverageDuration = singleAverageDuration;
	}

	public long getSingleMassDuration() {
		return singleMassDuration;
	}

	public void setSingleMassDuration(long singleMassDuration) {
		this.singleMassDuration = singleMassDuration;
	}

}
