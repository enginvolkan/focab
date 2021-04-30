package com.engin.focab.dtos;

public class BugDto {
	String text;
	String type;
	String[] sentences;

	public BugDto() {
		super();
	}
	public BugDto(String text, String type, String[] sentences) {
		super();
		this.text = text;
		this.type = type;
		this.sentences = sentences;
	}
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getSentences() {
		return sentences;
	}

	public void setSentences(String[] sentences) {
		this.sentences = sentences;
	}

}
