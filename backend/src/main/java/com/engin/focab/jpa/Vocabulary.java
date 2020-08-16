package com.engin.focab.jpa;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class Vocabulary {
	public Vocabulary(List<Definition> def) {
		definitions = def;
	}

	public Vocabulary() {
	}

	@Id
	private String text;
	@OneToMany
	private List<Definition> definitions;
	@OneToMany
	private Set<Example> examples = new HashSet<Example>();

	public Vocabulary(String text, List<Definition> def) {
		this(text);
		this.definitions = def;
	}

	public Vocabulary(String text) {
		this.text = text;

	}

	public List<Definition> getDefinitions() {
		return definitions;
	}

	public void setDefinitions(List<Definition> definitions) {
		this.definitions = definitions;
	}

	public Set<Example> getExamples() {
		return examples;
	}

	public void setExamples(Set<Example> examples) {
		this.examples = examples;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
