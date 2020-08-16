package com.engin.focab.jpa.corpus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.engin.focab.jpa.Vocabulary;

@Entity
@Table(name = "idioms")
public class IdiomModel extends Vocabulary {

	@Column(columnDefinition = "TEXT")
	private String regex;

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getRegex() {
		return regex;
	}

	@Override
	public String toString() {
		return super.getText();
	}

}
