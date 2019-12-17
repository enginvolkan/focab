package com.engin.focab.jpa;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "subtitles")
public class SubtitleModel {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private int pk;
	private int id;

	@ManyToOne
	MovieAnalysisModel movie;

	private String startTime;
	private String endTime;
	private String text;
	private long timeIn;
	private long timeOut;
	@OneToOne
	private SubtitleModel nextSubtitle;

	private String[] singleWords;
	private String[] adjNounTuples;

	@Column(name = "idioms", columnDefinition = "LONGTEXT")
	@ElementCollection
	private Set<String> idioms;

//	@Column(name = "idioms", columnDefinition = "LONGTEXT")
//	private String[] idioms;
	@Column(name = "phrasals", columnDefinition = "LONGTEXT")
	@ElementCollection
	private Set<String> phrasalVerbs;
	@Column(name = "collocations", columnDefinition = "LONGTEXT")
	@ElementCollection
	private Set<String> collocations;

	@Override
	public String toString() {
		return "SubtitleModel [id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", text=" + text
				+ ", timeIn=" + timeIn + ", timeOut=" + timeOut + "]";
	}

	public Set<String> getIdioms() {
		return idioms;
	}

	public void setIdioms(Set<String> idioms) {
		this.idioms = idioms;
	}

	public String[] getSingleWords() {
		return singleWords;
	}

	public void setSingleWords(String[] singleWords) {
		this.singleWords = singleWords;
	}

	public Set<String> getPhrasalVerbs() {
		return phrasalVerbs;
	}

	public void setPhrasalVerbs(Set<String> phrasalVerbs) {
		this.phrasalVerbs = phrasalVerbs;
	}

	public Set<String> getCollocations() {
		return collocations;
	}

	public void setCollocations(Set<String> collocations) {
		this.collocations = collocations;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(long timeIn) {
		this.timeIn = timeIn;
	}

	public long getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}

	public SubtitleModel getNextSubtitle() {
		return nextSubtitle;
	}

	public void setNextSubtitle(SubtitleModel nextSubtitle) {
		this.nextSubtitle = nextSubtitle;
	}

	public String[] getAdjNounTuples() {
		return adjNounTuples;
	}

	public void setAdjNounTuples(String[] adjNounTuples) {
		this.adjNounTuples = adjNounTuples;
	}

}