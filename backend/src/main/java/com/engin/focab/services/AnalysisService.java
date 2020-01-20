package com.engin.focab.services;

import java.net.MalformedURLException;

import org.apache.xmlrpc.XmlRpcException;

import com.engin.focab.jpa.MovieAnalysisModel;
import com.engin.focab.jpa.SubtitleModel;
import com.engin.focab.jpa.SummerizedMovieAnalysisModel;

public interface AnalysisService {
	public MovieAnalysisModel analyzeMovie(String imdbId) throws MalformedURLException, XmlRpcException;

	public SubtitleModel analyzeSentence(String sentence, boolean analyseIdioms, boolean analysePhrasalVerbs,
			boolean analyseSingleWords);

	public SummerizedMovieAnalysisModel summerizeMovie(String imdbId);

}
