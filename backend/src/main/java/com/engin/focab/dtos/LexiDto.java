package com.engin.focab.dtos;

import java.util.List;

public class LexiDto {
	Long id;
	String text;
	List<String> definitions;
	List<String> movieExamples;
	List<String> otherExamples;

	public LexiDto(Long id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	public LexiDto(String text) {
		super();
		this.text = text;
	}
	public List<String> getDefinitions() {
		return definitions;
	}

	public void setDefinitions(List<String> definitions) {
		this.definitions = definitions;
	}

	public String getText() {
		return text;
	}

	public List<String> getMovieExamples() {
		return movieExamples;
	}

	public void setMovieExamples(List<String> movieExamples) {
		this.movieExamples = movieExamples;
	}

	public List<String> getOtherExamples() {
		return otherExamples;
	}

	public void setOtherExamples(List<String> otherExamples) {
		this.otherExamples = otherExamples;
	}

}
