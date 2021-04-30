package com.engin.focab.jpa.corpus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bug")

public class BugModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column()
	private String text;

	@Column()
	private String type;

	@Column(columnDefinition = "TEXT")
	private String sentences;

	// Constructors
	public BugModel() {
	}

	public BugModel(String text, String type, String sentences) {
		this(text);
		this.type = type;
		this.sentences = sentences;
	}

	public BugModel(String text) {
		this.text = text;
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

	public String getSentences() {
		return sentences;
	}

	public void setSentences(String sentences) {
		this.sentences = sentences;
	}

	public Long getId() {
		return id;
	}

}
