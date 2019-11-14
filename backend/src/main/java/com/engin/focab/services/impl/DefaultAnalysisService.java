package com.engin.focab.services.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.xmlrpc.XmlRpcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.engin.focab.jpa.MovieAnalysisModel;
import com.engin.focab.jpa.SubtitleModel;
import com.engin.focab.jpa.corpus.IdiomModel;
import com.engin.focab.repository.IdiomRepository;
import com.engin.focab.repository.MovieAnalysisRepository;
import com.engin.focab.services.AnalysisService;
import com.engin.focab.services.IndexedSearchService;
import com.engin.focab.services.SrtParserService;
import com.engin.focab.services.SubtitleService;
import com.github.wtekiela.opensub4j.response.SubtitleFile;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

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

	@Autowired
	private IndexedSearchService indexedSearchService;

	@Override
	public MovieAnalysisModel analyzeMovie(String imdbId) throws MalformedURLException, XmlRpcException {
		MaxentTagger tagger = new MaxentTagger(
				"/home/engin/Downloads/stanford-postagger-full-2018-10-16/models/wsj-0-18-left3words-nodistsim.tagger");

		MovieAnalysisModel analysisResult = movieAnalysisRepository.findMovieAnalysisByImdbId(imdbId);
		StringBuilder longString = new StringBuilder();
		String taggedSentence;
		String[] taggedArray;

		HashSet<String> idiomSet = new HashSet<String>();

		if (analysisResult == null) {
			SubtitleFile file = subtitleService.getASubtitleByImdbId(imdbId); // 0702019 0248654
			ArrayList<SubtitleModel> subtitles = srtParserService
					.getSubtitlesFromString(file.getContentAsString("UTF-8"));

			for (Iterator iterator = subtitles.iterator(); iterator.hasNext();) {
				SubtitleModel subtitle = (SubtitleModel) iterator.next();
				longString.append(System.getProperty("line.separator"));
				longString.append(subtitle.text);
				longString.append(System.getProperty("line.separator"));
				taggedSentence = tagger.tagString(subtitle.text);
				longString.append(taggedSentence);
				longString.append(System.getProperty("line.separator"));

				taggedArray = taggedSentence.split(" ");
				idiomSet.addAll(findIdioms(taggedArray));
				if (!idiomSet.isEmpty()) {
					longString.append("------------------");
					longString.append(System.getProperty("line.separator"));
					for (Iterator iterator2 = idiomSet.iterator(); iterator2.hasNext();) {
						IdiomModel subtitleModel = (IdiomModel) iterator2.next();
						longString.append(subtitleModel.getIdiom());
						longString.append(System.getProperty("line.separator"));
					}
					longString.append("------------------");
					longString.append(System.getProperty("line.separator"));

				}
				longString.append("******************");
				longString.append(System.getProperty("line.separator"));

//
//				String first = "_VB";
//				String[] next = { "_IN", "_RP" };
//				String[] omit = { "_PRP" };
//
//				String[] collocations = filter(taggedSentence, first, next, omit);
//				for (String collocation : collocations) {
//					System.err.println("Collocation found: " + collocation);
//				}
//
//				String first2 = "_JJ";
//				String[] next2 = { "_NN" };
//				String[] omit2 = {};
//
//				String[] nounNouns = filter(taggedSentence, first2, next2, omit2);
//				for (String nounNoun : nounNouns) {
//					System.err.println("Adjective+nouns found: " + nounNoun);
//				}
//					pos(subtitle.text);

			}
			return new MovieAnalysisModel(longString.toString());
		}

		// Process each sentence
		//// determine parts of speech
		//// find idioms
		//// find phrasal verbs
		//// find adj+noun tuples
		//// detect single words

		return analysisResult;
	}

	private HashSet<String> findIdioms(String[] taggedSentence) throws MalformedURLException {
		int beginIndex = 0;
		int endIndex = 0;
		HashSet<String> previousSet = new HashSet<String>();
		HashSet<String> currentSet = new HashSet<String>();
		HashSet<String> foundSet = new HashSet<String>();

		for (int i = 0; i < taggedSentence.length; i++) {
			if (!extractTag(taggedSentence[i]).equals("PRP")) {
				previousSet = (HashSet<String>) currentSet.clone();
				HashSet<String> idioms = searchWordInIdiomRepository(extractWord(taggedSentence[i]));
				if (currentSet.isEmpty()) {
					currentSet.addAll(idioms);
				} else {
					currentSet.retainAll(idioms);
				}
				if (currentSet.isEmpty()) {

					if (!previousSet.isEmpty()) {
						int foundLength = endIndex - beginIndex;
						String firstIdiom = previousSet.iterator().next();
						int idiomLength = firstIdiom.length() - foundLength;
						if (Math.abs(foundLength - idiomLength) < 1) {
							foundSet.add(firstIdiom);
						}
					}
					endIndex = i + 1;
					beginIndex = i + 1;
				} else {
					currentSet = (HashSet<String>) idioms.clone();
					endIndex = i;
				}
			}
		}
		return foundSet;
	}

	private HashSet<String> searchWordInIdiomRepository(String word) throws MalformedURLException {
		return indexedSearchService.findIdiomsByWord(word);
	}

	private String[] filter(String[] sentence, String first, String[] next, String[] omit) {

		ArrayList<String> wordsList = new ArrayList<String>(Arrays.asList(sentence));
		HashSet<String> nextSet = new HashSet<String>(Arrays.asList(next));
		HashSet<String> omitSet = new HashSet<String>(Arrays.asList(omit));
		ArrayList<String> collocations = new ArrayList<String>();

		int beginIndex = 0;
		int endIndex = 0;

		for (int i = 0; i < wordsList.size(); i++) {
			String w = extractTag((String) wordsList.get(i));
			if (w.equals(first)) {
				beginIndex = i;
			} else {
				if (beginIndex > 0 && omitSet.contains(extractTag(w))) {
				} else {
					if (beginIndex > 0 && nextSet.contains(extractTag(w))) {
						String nextWord = wordsList.get(i + 1);
						if (nextSet.contains(extractTag(nextWord))) {
							endIndex = i + 1;
						} else {
							endIndex = i;
						}
						if (endIndex > 0) {
							String collocation = "";
							for (int j = beginIndex; j < endIndex + 1; j++) {
								collocation = collocation + " " + (String) wordsList.get(j);
							}
							collocations.add(collocation);
							beginIndex = 0;
							endIndex = 0;
						}
					} else {
						beginIndex = 0;
					}
				}

			}
		}

		return collocations.toArray(new String[0]);
	}

	private String extractTag(String s) {
		String tag = s.substring(s.indexOf('_') + 1, s.length());
		if (tag.length() > 2) {
			return tag.substring(0, 3);
		} else {
			return tag;
		}
	}

	private String extractWord(String s) {
		return s.substring(0, s.indexOf('_'));
	}
}
