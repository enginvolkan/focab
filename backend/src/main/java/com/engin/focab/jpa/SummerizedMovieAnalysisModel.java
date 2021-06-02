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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pk;

	private String imdbId;

	private ArrayList<LexiDto> idioms = new ArrayList<>();

	private ArrayList<LexiDto> phrasalVerbs = new ArrayList<>();

	private ArrayList<LexiDto> singleWords = new ArrayList<>();

	private long lemmaTotalDuration;
	private long idiomTotalDuration;
	private long phrasalTotalDuration;
	private long singleTotalDuration;
	private long aggregationDuration;
	private long downloadDuration;
	private long dbsaveDuration;

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

	public long getIdiomTotalDuration() {
		return idiomTotalDuration;
	}

	public void setIdiomTotalDuration(long idiomTotalDuration) {
		this.idiomTotalDuration = idiomTotalDuration;
	}

	public long getPhrasalTotalDuration() {
		return phrasalTotalDuration;
	}

	public void setPhrasalTotalDuration(long phrasalTotalDuration) {
		this.phrasalTotalDuration = phrasalTotalDuration;
	}

	public long getAggregationDuration() {
		return aggregationDuration;
	}

	public void setAggregationDuration(long aggregationDuration) {
		this.aggregationDuration = aggregationDuration;
	}

	public long getSingleTotalDuration() {
		return singleTotalDuration;
	}

	public void setSingleTotalDuration(long singleTotalDuration) {
		this.singleTotalDuration = singleTotalDuration;
	}

	public long getDownloadDuration() {
		return downloadDuration;
	}

	public void setDownloadDuration(long downloadDuration) {
		this.downloadDuration = downloadDuration;
	}

	public long getLemmaTotalDuration() {
		return lemmaTotalDuration;
	}

	public void setLemmaTotalDuration(long lemmaTotalDuration) {
		this.lemmaTotalDuration = lemmaTotalDuration;
	}

	public long getDbsaveDuration() {
		return dbsaveDuration;
	}

	public void setDbsaveDuration(long dbsaveDuration) {
		this.dbsaveDuration = dbsaveDuration;
	}

}
