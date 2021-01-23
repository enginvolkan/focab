package com.engin.focab.jpa;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.engin.focab.jpa.corpus.LexiModel;

@Embeddable
public class FavoriteEntry {
	@NotNull
	@OneToOne
	private LexiModel lexiModel;
	public FavoriteEntry() {
	}	
	public FavoriteEntry(LexiModel lexiModel) {
		super();
		this.lexiModel = lexiModel;
	}

	public LexiModel getVocabulary() {
		return lexiModel;
	}

	public void setVocabulary(LexiModel lexiModel) {
		this.lexiModel = lexiModel;
	}
	
	
	
	
}
