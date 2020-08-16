package com.engin.focab.jpa;

import javax.persistence.Entity;

@Entity
public class SingleWord extends Vocabulary {
	private boolean commonword;
	private int commonWordLevel;

	public SingleWord(String text, boolean commonWord, int level) {
		super(text);
		this.commonword = commonWord;
		this.commonWordLevel = level;
	}

	public SingleWord(String text) {
		super(text);
	}

	public boolean isCommonword() {
		return commonword;
	}

	public int getCommonWordLevel() {
		return commonWordLevel;
	}

}
