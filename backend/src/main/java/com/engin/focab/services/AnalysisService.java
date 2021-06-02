package com.engin.focab.services;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.xmlrpc.XmlRpcException;

import com.engin.focab.jpa.SubtitleModel;
import com.engin.focab.jpa.SummerizedMovieAnalysisModel;

public interface AnalysisService {
	public SummerizedMovieAnalysisModel analyzeMovie(String imdbId) throws MalformedURLException, XmlRpcException;

	public SubtitleModel analyzeSentence(String sentence, boolean analyseIdioms, boolean analysePhrasalVerbs,
			boolean analyseSingleWords);

	public List<SubtitleModel> getFullSubtitles(String imdbId);

}
