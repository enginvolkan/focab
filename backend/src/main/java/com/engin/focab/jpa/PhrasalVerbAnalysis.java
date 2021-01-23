package com.engin.focab.jpa;

import java.util.Set;

public class PhrasalVerbAnalysis {
	private Set<String> phrasalVerbSet;
	private String trace;

	public Set<String> getPhrasalVerbSet() {
		return phrasalVerbSet;
	}

	public void setPhrasalVerbSet(Set<String> phrasalVerbSet) {
		this.phrasalVerbSet = phrasalVerbSet;
	}

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

	public PhrasalVerbAnalysis(Set<String> phrasalVerbSet, String trace) {
		super();
		this.phrasalVerbSet = phrasalVerbSet;
		this.trace = trace;
	}

}
