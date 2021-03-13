package com.engin.focab.jpa.corpus;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "lexi")

public class LexiModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String text;

	@Enumerated(EnumType.STRING)
	private LexiType type;
	// Single Word related fields
	private Integer commonWordLevel;

	// Idiom related fields

	@Column(columnDefinition = "TEXT")
	private String regex;

	// Phrasal Verb related fields
	// @Column(columnDefinition = "boolean default true")
	private Boolean isSeparable;

	public void setSeperable(boolean isSeparable) {
		this.isSeparable = isSeparable;
	}

	@OneToMany
	private List<DefinitionModel> definitionModels;

	// Constructors
	public LexiModel() {
	}

	public LexiModel(String text, List<DefinitionModel> def) {
		this(text);
		this.definitionModels = def;
	}

	public LexiModel(String text) {
		this.text = text;
	}

	public LexiModel(String text, int level) {
		this.text = text;
		this.commonWordLevel = level;
	}

	// Getters and Setters

	public Integer getCommonWordLevel() {
		return commonWordLevel;
	}

	public List<DefinitionModel> getDefinitions() {
		return definitionModels;
	}

	public void setDefinitions(List<DefinitionModel> definitionModels) {
		this.definitionModels = definitionModels;
	}

	public String getText() {
		return text;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getRegex() {
		return regex;
	}

	public boolean isSeparable() {
		return isSeparable;
	}

	public Long getId() {
		return id;
	}

}

enum LexiType {
	SINGLE, PHRASAL, IDIOM
}
