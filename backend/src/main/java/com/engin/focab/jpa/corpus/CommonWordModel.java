package com.engin.focab.jpa.corpus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "commonwords")
public class CommonWordModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(columnDefinition = "TEXT")
	private String word;
	
	private int rank;

	public CommonWordModel() {
	}
	public CommonWordModel(String word) {
		super();
		this.word = word;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public long getId() {
		return id;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

}
