package com.engin.focab.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vocabulary {
    public Vocabulary(String string) {
    	Vocabulary v = new Vocabulary();
    	v.text = string;
    }
	public Vocabulary() {
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String text;
    
    // standard constructors

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getId() {
		return id;
	}
 
 
    // standard getters and setters
}
