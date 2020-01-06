package com.engin.focab.jpa.corpus;

import java.util.Set;

public class IdiomAnalysis {
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

	public IdiomAnalysis(Set<String> idiomSet, String trace) {
		super();
		this.idiomSet = idiomSet;
		this.trace = trace;
	}

}
