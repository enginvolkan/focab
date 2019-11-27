package com.engin.focab.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "movieanalysis")

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

	@OneToMany(cascade = CascadeType.PERSIST)
	private List<SubtitleModel> idioms = new ArrayList<SubtitleModel>();

	@Column(columnDefinition = "LONGTEXT")
	private String fullSubtitles;

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public Long getId() {
		return id;
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

	public void setId(Long id) {
		this.id = id;
	}

	public void setIdioms(List<SubtitleModel> idioms) {
		this.idioms = idioms;
	}

}
