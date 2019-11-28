package com.engin.focab.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Definition {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String definition;
	private boolean isSeparable;

	@OneToMany
	private Set<Example> examples = new HashSet<Example>();
	@ManyToOne
	private Vocabulary vocabulary;

	public Definition(String text, String definition, boolean isSeparable, String example, String source) {
		this.definition = definition;
		this.isSeparable = isSeparable;
		this.examples.add(new Example(example, source, null, vocabulary));
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

	public Set<Example> getExamples() {
		return examples;
	}

	public void setExamples(Set<Example> examples) {
		this.examples = examples;
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
