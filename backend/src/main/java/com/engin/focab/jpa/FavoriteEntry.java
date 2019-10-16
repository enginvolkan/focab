package com.engin.focab.jpa;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Embeddable
public class FavoriteEntry {
	@NotNull
	@OneToOne
	private Vocabulary vocabulary;
	public FavoriteEntry() {
	}	
	public FavoriteEntry(Vocabulary vocabulary) {
		super();
		this.vocabulary = vocabulary;
	}

	public Vocabulary getVocabulary() {
		return vocabulary;
	}

	public void setVocabulary(Vocabulary vocabulary) {
		this.vocabulary = vocabulary;
	}
	
	
	
	
}
