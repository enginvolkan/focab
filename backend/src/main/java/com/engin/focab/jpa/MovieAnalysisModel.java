package com.engin.focab.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "movieanalysis")

public class MovieAnalysisModel {

	//////////////
	//Constractors
	//////////////
	public MovieAnalysisModel(String string) {
		super();
		this.imdbId = string;
	}

	public MovieAnalysisModel() {
		super();
	}

	//////////////
	//Fields
	//////////////
	@Id
	private String imdbId;

	@OneToMany(cascade = CascadeType.PERSIST)
	private List<SubtitleModel> idioms = new ArrayList<SubtitleModel>();

	@OneToMany(cascade = CascadeType.PERSIST)
	private List<SubtitleModel> phrasalVerbs = new ArrayList<SubtitleModel>();

	@OneToMany(cascade = CascadeType.PERSIST)
	private List<SubtitleModel> singleWords = new ArrayList<SubtitleModel>();

	@OneToMany(cascade = CascadeType.PERSIST)
	private List<SubtitleModel> emptySubtitles = new ArrayList<SubtitleModel>();

	@Column(columnDefinition = "LONGTEXT")
	private String fullSubtitles;

	/////////////////////
	//Getters and Setters
	/////////////////////
	public String getImdbId() {
		return imdbId;
	}

	public List<SubtitleModel> getIdioms() {
		return idioms;
	}

	public void setIdioms(ArrayList<SubtitleModel> idioms) {
		this.idioms = idioms;
	}

	public String getFullSubtitles() {
		return fullSubtitles;
	}

	public void setFullSubtitles(String fullSubtitles) {
		this.fullSubtitles = fullSubtitles;
	}

	public List<SubtitleModel> getPhrasalVerbs() {
		return phrasalVerbs;
	}

	public void setPhrasalVerbs(List<SubtitleModel> phrasalVerbs) {
		this.phrasalVerbs = phrasalVerbs;
	}

	public List<SubtitleModel> getSingleWords() {
		return singleWords;
	}

	public void setSingleWords(List<SubtitleModel> singleWords) {
		this.singleWords = singleWords;
	}

	public List<SubtitleModel> getEmptySubtitles() {
		return emptySubtitles;
	}

	public void setEmptySubtitles(List<SubtitleModel> emptySubtitles) {
		this.emptySubtitles = emptySubtitles;
	}

	public void setIdioms(List<SubtitleModel> idioms) {
		this.idioms = idioms;
	}

}
