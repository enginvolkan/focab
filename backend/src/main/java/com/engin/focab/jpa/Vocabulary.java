package com.engin.focab.jpa;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vocabulary {
    public Vocabulary(List<String> def) {
    	definitions = def;
    }
	public Vocabulary() {
	}
	
	@Id
    private String id;
	@ElementCollection
    private List<String> definitions;


	// standard constructors
    public Vocabulary(String id, List<String> def) {
		super();
		this.id = id;
		this.definitions = def;
	}
    public Vocabulary(String id) {
		super();
		this.id = id;
	}   
    
    // standard getters and setters
	public List<String> getDefinitions() {
		return definitions;
	}
	public void setText(List<String>  def) {
		this.definitions = def;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id=id;
	}
	
}
