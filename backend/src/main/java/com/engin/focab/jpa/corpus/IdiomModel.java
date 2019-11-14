package com.engin.focab.jpa.corpus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "idioms")
public class IdiomModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(columnDefinition = "TEXT")
	private String idiom;

	@Column(columnDefinition = "TEXT")
	private String description;

	public IdiomModel(String idiom) {
		super();
		this.idiom = idiom;
	}

	public IdiomModel(String string, String string2) {
		this(string2);
		this.id = Integer.parseInt(string);
	}

	public String getIdiom() {
		return idiom;
	}

	@Override
	public String toString() {
		return idiom;
	}

}