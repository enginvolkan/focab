package com.engin.focab.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="movieanalysis")

public class MovieAnalysisModel {
	public MovieAnalysisModel(String string) {
		super();
		this.imdbId = string;
	}
	
	public MovieAnalysisModel() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	private String imdbId;
	
	private String[] singleWords;
	private String[] adjNounTuples;
	private String[] idioms;
	private String[] phrasalVerbs;
	private String[] collocations;
	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public String[] getSingleWords() {
		return singleWords;
	}

	public void setSingleWords(String[] singleWords) {
		this.singleWords = singleWords;
	}

	public String[] getAdjNounTuples() {
		return adjNounTuples;
	}

	public void setAdjNounTuples(String[] adjNounTuples) {
		this.adjNounTuples = adjNounTuples;
	}

	public String[] getIdioms() {
		return idioms;
	}

	public void setIdioms(String[] idioms) {
		this.idioms = idioms;
	}

	public String[] getPhrasalVerbs() {
		return phrasalVerbs;
	}

	public void setPhrasalVerbs(String[] phrasalVerbs) {
		this.phrasalVerbs = phrasalVerbs;
	}

	public String[] getCollocations() {
		return collocations;
	}

	public void setCollocations(String[] collocations) {
		this.collocations = collocations;
	}

	public Long getId() {
		return id;
	}

	

}
