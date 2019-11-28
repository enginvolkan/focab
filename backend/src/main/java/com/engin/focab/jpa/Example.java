package com.engin.focab.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Example {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String text;
	private String source;
	private String reference;

	@ManyToOne
	private Vocabulary vocabulary;
	@ManyToOne
	private Definition definition;

	public Example() {
		super();
	}

	public Example(@NotNull String text, String source, String reference, Vocabulary vocabulary) {
		this();
		this.text = text;
		this.source = source;
		this.reference = reference;
		this.vocabulary = vocabulary;
	}

	public Example(@NotNull String text, String source, String reference, Definition definition) {
		this(text, source, reference, definition.getVocabulary());
		this.definition = definition;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Vocabulary getVocabulary() {
		return vocabulary;
	}

	public void setVocabulary(Vocabulary vocabulary) {
		this.vocabulary = vocabulary;
	}

	public Long getId() {
		return id;
	}

}
