package com.engin.focab.jpa.corpus;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.engin.focab.jpa.Vocabulary;

@Entity
@Table(name = "commonwords")
public class CommonWordModel extends Vocabulary {

	private int level;

	public CommonWordModel() {
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
