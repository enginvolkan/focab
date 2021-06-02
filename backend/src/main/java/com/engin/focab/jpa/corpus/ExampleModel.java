package com.engin.focab.jpa.corpus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class ExampleModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String text;
	private String source;
	private String reference;

	@ManyToOne
	private LexiModel lexiModel;
	@ManyToOne
	private DefinitionModel definitionModel;

	public ExampleModel() {
		super();
	}

	public ExampleModel(@NotNull String text, String source, String reference, LexiModel lexiModel) {
		this();
		this.text = text;
		this.source = source;
		this.reference = reference;
		this.lexiModel = lexiModel;
	}

	public ExampleModel(@NotNull String text, String source, String reference, DefinitionModel definitionModel) {
		this(text, source, reference, definitionModel.getVocabulary());
		this.definitionModel = definitionModel;
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

	public LexiModel getVocabulary() {
		return lexiModel;
	}

	public void setVocabulary(LexiModel lexiModel) {
		this.lexiModel = lexiModel;
	}

	public Long getId() {
		return id;
	}

}
