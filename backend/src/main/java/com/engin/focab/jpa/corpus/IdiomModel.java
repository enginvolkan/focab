package com.engin.focab.jpa.corpus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="idioms")
public class IdiomModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(columnDefinition = "TEXT")
	private String idiom;

	public IdiomModel(String idiom) {
		super();
		this.idiom = idiom;
	}

	public String getIdiom() {
		return idiom;
	}
}
