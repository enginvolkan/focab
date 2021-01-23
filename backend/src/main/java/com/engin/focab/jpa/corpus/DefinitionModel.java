package com.engin.focab.jpa.corpus;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class DefinitionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String definition;
	private boolean isSeparable;

	@OneToMany
	private Set<ExampleModel> exampleModels = new HashSet<ExampleModel>();
	@ManyToOne
	private LexiModel lexiModel;

	public DefinitionModel() {
		super();
	}

	public DefinitionModel(String text, String definition, boolean isSeparable, String example, String source) {
		this.definition = definition;
		this.isSeparable = isSeparable;
		this.exampleModels.add(new ExampleModel(example, source, null, lexiModel));
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public boolean isSeparable() {
		return isSeparable;
	}

	public void setSeparable(boolean isSeparable) {
		this.isSeparable = isSeparable;
	}

	public Set<ExampleModel> getExamples() {
		return exampleModels;
	}

	public void setExamples(Set<ExampleModel> exampleModels) {
		this.exampleModels = exampleModels;
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
