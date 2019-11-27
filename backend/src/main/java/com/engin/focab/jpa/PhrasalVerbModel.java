package com.engin.focab.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class PhrasalVerbModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String verb;
	private String type;
	private String meaning;
	private String example;
	private String reference;

	public PhrasalVerbModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PhrasalVerbModel(@NotNull String verb, String meaning, String reference, String type, String example) {
		super();
		this.verb = verb;
		this.meaning = meaning;
		this.reference = reference;
		this.type = type;
		this.example = example;

	}

	public String getVerb() {
		return verb;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return verb + "," + meaning + "," + type + "," + example + "," + reference;
	}

}
