package com.engin.focab.services.impl;

import java.io.BufferedReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.xmlrpc.XmlRpcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.engin.focab.jpa.MovieAnalysisModel;
import com.engin.focab.jpa.SubtitleModel;
import com.engin.focab.repository.IdiomRepository;
import com.engin.focab.repository.MovieAnalysisRepository;
import com.engin.focab.services.AnalysisService;
import com.engin.focab.services.SrtParserService;
import com.engin.focab.services.SubtitleService;
import com.github.wtekiela.opensub4j.response.SubtitleFile;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import com.engin.focab.services.impl.srt.DefaultSrtParserService;

@Component
public class DefaultAnalysisService implements AnalysisService {

	@Autowired
	private MovieAnalysisRepository movieAnalysisRepository;

	@Autowired
	private SubtitleService subtitleService;

	@Autowired
	private SrtParserService srtParserService;
	
	@Autowired
	private IdiomRepository idiomRepository;
	
	
	@Override
	public MovieAnalysisModel analyzeMovie(String imdbId) throws MalformedURLException, XmlRpcException {
		MaxentTagger tagger = new MaxentTagger("/home/engin/Downloads/stanford-postagger-full-2018-10-16/models/wsj-0-18-left3words-nodistsim.tagger");

		MovieAnalysisModel analysisResult = movieAnalysisRepository.findMovieAnalysisByImdbId(imdbId);
		StringBuilder longString = new StringBuilder();
		String[] taggedSentence;

		if (analysisResult == null) {
			SubtitleFile file = subtitleService.getASubtitleByImdbId("0248654"); // 0702019
			ArrayList<SubtitleModel> subtitles = srtParserService
					.getSubtitlesFromString(file.getContentAsString("UTF-8"));

			for (Iterator iterator = subtitles.iterator(); iterator.hasNext();) {
				SubtitleModel subtitle = (SubtitleModel) iterator.next();
				System.out.println(subtitle.text);
				longString.append(subtitle.text);
				taggedSentence = tagger.tagString(subtitle.text).split(" ");
				System.err.println(taggedSentence);
				
				findIdioms();

				String first = "_VB";
				String[] next = { "_IN", "_RP" };
				String[] omit = { "_PRP" };

				String[] collocations = filter(taggedSentence, first, next, omit);
				for (String collocation : collocations) {
					System.err.println("Collocation found: " + collocation);
				}

				String first2 = "_JJ";
				String[] next2 = { "_NN" };
				String[] omit2 = {};

				String[] nounNouns = filter(taggedSentence, first2, next2, omit2);
				for (String nounNoun : nounNouns) {
					System.err.println("Adjective+nouns found: " + nounNoun);
				}
//					pos(subtitle.text);

			}
		}

		// Process each sentence
		//// determine parts of speech
		//// find idioms
		//// find phrasal verbs
		//// find adj+noun tuples
		//// detect single words

		return analysisResult;
	}
	private void findIdioms() {
//		MysqlDataSource sphinxDataSource = new MysqlDataSource();
//		Connection conn = sphinxDataSource.getConnection();
	}
	private String[] filter(String[] sentence,String first,String[] next, String[] omit) {

		ArrayList<String> wordsList = new ArrayList<String>(Arrays.asList(sentence));
		HashSet<String> nextSet = new HashSet<String>(Arrays.asList(next));
		HashSet<String> omitSet = new HashSet<String>(Arrays.asList(omit));
		ArrayList<String> collocations= new ArrayList<String>();
		
		int beginIndex=0;
		int endIndex=0;
		
		for (int i = 0; i < wordsList.size(); i++) {
			String w = extractTag((String) wordsList.get(i));
			if(w.equals(first)) {
				beginIndex=i;
			}else {
				if(beginIndex > 0 && omitSet.contains(extractTag(w))){
				}else {
					if(beginIndex > 0 && nextSet.contains(extractTag(w))){
						String nextWord = wordsList.get(i+1);
						if(nextSet.contains(extractTag(nextWord))){
							endIndex=i+1;
						}else {
							endIndex=i;
						}
						if(endIndex>0) {
							String collocation="";
							for (int j = beginIndex; j < endIndex+1; j++) {
								collocation = collocation + " " + (String) wordsList.get(j);
							}
							collocations.add(collocation);
							beginIndex=0;
							endIndex=0;
						}						
					}
					else {
						beginIndex=0;
					}
				}
				
			}
		}
		
		return collocations.toArray(new String[0]);
	}
	
	private String extractTag(String s) {
		String tag = s.substring(s.indexOf('_'),s.length());
		if(tag.length()>2) {
		return tag.substring(0,3);
		}else {
			return tag;
		}
	}
}
