package com.engin.focab.jpa;

import java.util.List;

import com.engin.focab.jpa.corpus.LexiModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Meta {

	@SerializedName("time")
	@Expose
	private double time;
	@SerializedName("total")
	@Expose
	private int total;
	@SerializedName("total_found")
	@Expose
	private int totalFound;
	@SerializedName("words")
	@Expose
	private List<Object> words = null;

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotalFound() {
		return totalFound;
	}

	public void setTotalFound(int totalFound) {
		this.totalFound = totalFound;
	}

	public List<Object> getWords() {
		return words;
	}

	public void setWords(List<Object> words) {
		this.words = words;
	}

}

public class SphinxResultModel {

	@SerializedName("attrs")
	@Expose
	private List<String> attrs = null;
	@SerializedName("matches")
	@Expose
	private List<List<LexiModel>> matches = null;
	@SerializedName("meta")
	@Expose
	private Meta meta;

	public List<String> getAttrs() {
		return attrs;
	}

	public void setAttrs(List<String> attrs) {
		this.attrs = attrs;
	}

	public List<List<LexiModel>> getMatches() {
		return matches;
	}

	public void setMatches(List<List<LexiModel>> matches) {
		this.matches = matches;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

}