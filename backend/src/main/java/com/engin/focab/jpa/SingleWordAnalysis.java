package com.engin.focab.jpa;

import java.util.Set;

public class SingleWordAnalysis {
	private Set<String> idiomSet;
	private String trace;

	public Set<String> getIdiomSet() {
		return idiomSet;
	}

	public void setIdiomSet(Set<String> idiomSet) {
		this.idiomSet = idiomSet;
	}

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

	public SingleWordAnalysis(Set<String> idiomSet, String trace) {
		super();
		this.idiomSet = idiomSet;
		this.trace = trace;
	}

}
