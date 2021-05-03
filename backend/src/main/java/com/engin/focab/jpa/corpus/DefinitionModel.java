package com.engin.focab.jpa.corpus;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "definition")
public class DefinitionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String definition;


	private String examples;
	@ManyToOne
	private LexiModel lexiModel;

	public DefinitionModel() {
		super();
	}

	public DefinitionModel(LexiModel lexi, String definition, String examples) {
		this.definition = definition;
		this.examples=examples;
		this.lexiModel = lexi;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}


	public List<String> getExamples() {
		return Arrays.asList(examples.split("|"));
	}

	public void setExamples(String examples) {
		this.examples = examples;
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
