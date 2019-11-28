package com.engin.focab.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "PhrasalVerb")
@Table(name = "PhrasalVerbs")
public class PhrasalVerbModel extends Vocabulary {

	@Column(columnDefinition = "boolean default true")
	private boolean isPhrasalVerb;

}
