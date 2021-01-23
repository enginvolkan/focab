package com.engin.focab.jpa.corpus;

	public class DictionaryDefinition {


	private String tag;
	private String definition;


	public DictionaryDefinition() {
	}


	public String getTag() {
		return tag;
	}


	public void setTag(String tag) {
		this.tag = tag;
	}


	public String getDefinition() {
		return definition;
	}


	public void setDefinition(String definition) {
		this.definition = definition;
	}


	public DictionaryDefinition(String tag, String definition) {
		super();
		this.tag = tag;
		this.definition = definition;
	}


	
}
