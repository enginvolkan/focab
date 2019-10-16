package com.engin.focab.jpa;

import javax.persistence.Column;
import javax.persistence.Embeddable;
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
	
    @ManyToOne
    private Vocabulary vocabulary;
	
	@NotNull
	@Column(columnDefinition = "TEXT")
	private String text;	
	
	private String source;
	private String reference;
	
	public Example() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Example(@NotNull String text, String source, String reference) {
		super();
		this.text = text;
		this.source = source;
		this.reference = reference;
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
