package com.engin.focab.jpa;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Vocabulary {
    public Vocabulary(List<String> def) {
    	definitions = def;
    }
	public Vocabulary() {
	}
	
	@Id
    private String id;
	private String text;
	@ElementCollection
    private List<String> definitions;


	// standard constructors
    public Vocabulary(String text, List<String> def) {
		this(text);
		this.definitions = def;
	}

	public Vocabulary(String text) {
		this.text = text.toLowerCase().trim();
		this.id = this.text.replaceAll("/ /","+");

	}   
    
    @OneToMany
	private Set<Example> examples= new HashSet<Example>();

    
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
	public Set<Example> getExamples() {
		return examples;
	}
	public void setExamples(Set<Example> examples) {
		this.examples = examples;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setDefinitions(List<String> definitions) {
		this.definitions = definitions;
	}
	
}
