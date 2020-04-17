package com.engin.focab.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.engin.focab.jpa.MovieAnalysisModel;
import com.engin.focab.jpa.SubtitleModel;
import com.engin.focab.jpa.SummerizedMovieAnalysisModel;
import com.engin.focab.jpa.corpus.IdiomAnalysis;
import com.engin.focab.repository.MovieAnalysisRepository;
import com.engin.focab.repository.SubtitleRepository;
import com.engin.focab.services.AnalysisService;
import com.engin.focab.services.PhrasalVerbsDetectionService;
import com.engin.focab.services.SrtParserService;
import com.engin.focab.services.SubtitleService;
import com.github.wtekiela.opensub4j.response.SubtitleFile;

import edu.stanford.nlp.simple.Sentence;

@Component
public class DefaultAnalysisService implements AnalysisService {

	@Autowired
	private MovieAnalysisRepository movieAnalysisRepository;
	@Autowired
	private SubtitleRepository subtitleRepository;

	@Autowired
	private SubtitleService subtitleService;

	@Autowired
	private SrtParserService srtParserService;

	@Autowired
	private IdiomDetectionService idiomDetectionService;

	@Autowired
	@Qualifier("constituencyParser")
	private PhrasalVerbsDetectionService phrasalDetectionService;

	@Autowired
	private SingleWordDetectionService singleWordsDetectionService;

	@Autowired
	private SentenceTaggingService sentenceTaggingService;

	@Value("${movie.analysis.useStoredAnalysis}")
	private boolean useStoredAnalysis;

	@Override
	public SummerizedMovieAnalysisModel analyzeMovie(String imdbId) {
		MovieAnalysisModel analysisResult = null;

		HashMap<String, List<String>> aggregatedIdiomMap = new HashMap<String, List<String>>();
		HashMap<String, List<String>> aggregatedPhrasalverbMap = new HashMap<String, List<String>>();
		HashMap<String, List<String>> aggregatedSingleWordMap = new HashMap<String, List<String>>();

		if (useStoredAnalysis == true) {
			analysisResult = movieAnalysisRepository.findMovieAnalysisByImdbId(imdbId);
		}

		if (analysisResult == null) {
			analysisResult = new MovieAnalysisModel();
			imdbId = imdbId.substring(2);
			SubtitleFile file = subtitleService.getASubtitleByImdbId(imdbId); // 0702019 0248654
			analysisResult.setFullSubtitles(file.getContentAsString("UTF-8"));
			ArrayList<SubtitleModel> subtitles = srtParserService
					.getSubtitlesFromString(analysisResult.getFullSubtitles());
			ArrayList<SubtitleModel> idiomSubtitles = new ArrayList<SubtitleModel>();
			ArrayList<SubtitleModel> phrasalVerbSubtitles = new ArrayList<SubtitleModel>();
			ArrayList<SubtitleModel> singleWordSubtitles = new ArrayList<SubtitleModel>();

			//// process each subtitle
			for (Iterator<SubtitleModel> iterator = subtitles.iterator(); iterator.hasNext();) {
				SubtitleModel subtitle = iterator.next();
				Sentence sentence = new Sentence(subtitle.getText());

				//// find idioms
				Set<String> idiomSet = detectIdioms(sentence).getIdiomSet();
				if (!idiomSet.isEmpty()) {
					subtitle.setIdioms(idiomSet);
					idiomSubtitles.add(subtitle);
					for (String idiom : idiomSet) {
						addToMap(aggregatedIdiomMap, idiom, sentence.text());
					}

				}

				//// find phrasal verbs
				List<String> phrasalSet = detectPhrasalVerbs(sentence);
				if (!phrasalSet.isEmpty()) {
					subtitle.setPhrasalVerbs(phrasalSet);
					phrasalVerbSubtitles.add(subtitle);
					for (String phrasal : phrasalSet) {
						addToMap(aggregatedPhrasalverbMap, phrasal, sentence.text());
					}
				}

//				subtitleRepository.save(subtitle);
				//// detect single words
				Set<String> singleWordsSet = detectSingleWords(cleanPunctuation(subtitle.getText()));
				if (!singleWordsSet.isEmpty()) {
					subtitle.setSingleWords(
							singleWordsSet.stream().map(Object::toString).collect(Collectors.joining(",")));
					singleWordSubtitles.add(subtitle);
					for (String singleWord : singleWordsSet) {
						addToMap(aggregatedSingleWordMap, singleWord, sentence.text());
					}
				}

				//// find phrasal verbs
				//// find adj+noun tuples

			}

			analysisResult.setImdbId(imdbId);
			analysisResult.setIdioms(idiomSubtitles);
			analysisResult.setPhrasalVerbs(phrasalVerbSubtitles);
			analysisResult.setSingleWords(singleWordSubtitles);

//			movieAnalysisRepository.save(analysisResult);
//			subtitleRepository.saveAll(subtitles);
//			movieAnalysisRepository.save(analysisResult);
		}

		SummerizedMovieAnalysisModel summerizedAnalysis = new SummerizedMovieAnalysisModel(imdbId);
		summerizedAnalysis.setIdioms(aggregatedIdiomMap);
		summerizedAnalysis.setPhrasalVerbs(aggregatedPhrasalverbMap);
		summerizedAnalysis.setSingleWords(aggregatedSingleWordMap);
		return summerizedAnalysis;
	}

	@Override
	public SubtitleModel analyzeSentence(String sentence, boolean analyseIdioms, boolean analysePhrasalVerbs,
			boolean analyseSingleWords) {
		SubtitleModel subtitle = new SubtitleModel(sentence);
		Sentence nplSentence = new Sentence(sentence);
		StringBuilder trace = new StringBuilder();

		if (analyseIdioms) {
			IdiomAnalysis idiomAnalysis = detectIdioms(nplSentence);
			subtitle.setIdioms(idiomAnalysis.getIdiomSet());
			trace.append(idiomAnalysis.getTrace());
		}

		if (analysePhrasalVerbs) {
			List<String> phrasalVerbAnalysis = detectPhrasalVerbs(nplSentence);
			subtitle.setPhrasalVerbs(phrasalVerbAnalysis);
			// trace.append(phrasalVerbAnalysis.getTrace());
		}

		if (analyseSingleWords) {
			subtitle.setSingleWords(detectSingleWords(cleanPunctuation(sentence)).stream().map(Object::toString)
					.collect(Collectors.joining(",")));
		}

		subtitle.setTrace(trace.toString());
		return subtitle;
	}

	private List<String> detectPhrasalVerbs(Sentence sentence) {
		return phrasalDetectionService.detectPhrasalVerbs(sentence);
	}

	private IdiomAnalysis detectIdioms(Sentence sentence) {
		return idiomDetectionService.detectIdioms(sentenceTaggingService.tagString(sentence.text()), sentence);
	}

	private Set<String> detectSingleWords(String sentence) {
		Set<String> singleWordSet = new HashSet<String>();
		String[] taggedSentence = sentenceTaggingService.tagString(sentence);
		List<String> taggedList = Arrays.asList(taggedSentence);

		singleWordSet.addAll(taggedList.stream() // get rid of irrelevant tags
				.filter(x -> !sentenceTaggingService.extractTag(x, true).equals("NNP")
						&& !sentenceTaggingService.extractTag(x, true).equals("NNPS")
						&& !sentenceTaggingService.extractTag(x, true).equals(",")
						&& !sentenceTaggingService.extractTag(x, true).equals(":")
						&& !sentenceTaggingService.extractTag(x, true).equals("TO")
						&& !sentenceTaggingService.extractTag(x, true).equals("."))
				.collect(Collectors.toSet()));
		singleWordSet = singleWordSet.stream().map(x -> sentenceTaggingService.extractWord(x).toLowerCase())
				.collect(Collectors.toSet());

		if (!singleWordSet.isEmpty()) { // convert plural to singular
			String s = singleWordSet.stream().collect(Collectors.joining(" "));
			List<String> singleWordList = sentenceTaggingService.lemmas(new Sentence(s));
			singleWordSet.clear();
			singleWordSet.addAll(singleWordList);
		}

		singleWordSet.removeAll(singleWordsDetectionService.getCommonWords());
		singleWordSet.removeAll(singleWordsDetectionService.getKnownWords());

		return singleWordSet;
	}

	private void addToMap(HashMap<String, List<String>> m, String key, String value) {
		if (m.containsKey(key)) {
			List<String> list = m.get(key);
			list.add(value);
			m.put(key, list);
		} else {
			ArrayList<String> a = new ArrayList<String>();
			a.add(value);
			m.put(key, a);
		}
	}

	private String cleanPunctuation(String s) {
		return s.replaceAll("[^a-zA-Z '-]", " ");
	}

}
