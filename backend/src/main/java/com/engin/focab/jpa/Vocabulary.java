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

public class Vocabulary {
	public Vocabulary(List<Definition> def) {
		definitions = def;
	}

	public Vocabulary() {
	}

	@Id
	private String id;
	@OneToMany
	private List<Definition> definitions;
	@OneToMany
	private Set<Example> examples = new HashSet<Example>();

	public Vocabulary(String text, List<Definition> def) {
		this(text);
		this.definitions = def;
	}

	public Vocabulary(String text) {
		this.id = text;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
