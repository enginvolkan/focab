package com.engin.focab.services;

import java.net.MalformedURLException;

import org.apache.xmlrpc.XmlRpcException;

import com.engin.focab.jpa.MovieAnalysisModel;

public interface AnalysisService {
	public MovieAnalysisModel analyzeMovie(String imdbId) throws MalformedURLException, XmlRpcException;

	public MovieAnalysisModel analyzeSentence(String sentence, boolean analyseIdioms, boolean analysePhrasalVerbs,
			boolean analyseSingleWords);

}
