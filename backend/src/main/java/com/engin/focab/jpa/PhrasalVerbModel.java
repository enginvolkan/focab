package com.engin.focab.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "PhrasalVerb")
public class PhrasalVerbModel extends Vocabulary {

	@Column(columnDefinition = "boolean default true")
	private boolean isSeparable;

	public boolean isSeperable() {
		return isSeparable;
	}

	public void setSeperable(boolean isSeperable) {
		this.isSeparable = isSeperable;
	}

}
