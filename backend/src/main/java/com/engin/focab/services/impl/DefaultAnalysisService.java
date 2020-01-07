package com.engin.focab.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.engin.focab.jpa.MovieAnalysisModel;
import com.engin.focab.jpa.SubtitleModel;
import com.engin.focab.jpa.corpus.IdiomAnalysis;
import com.engin.focab.jpa.corpus.PhrasalVerbAnalysis;
import com.engin.focab.repository.MovieAnalysisRepository;
import com.engin.focab.repository.SubtitleRepository;
import com.engin.focab.services.AnalysisService;
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
	private PhrasalVerbsDetectionService phrasalDetectionService;

	@Autowired
	private SingleWordDetectionService singleWordsDetectionService;

	@Autowired
	private SentenceTaggingService sentenceTaggingService;

	@Value("${movie.analysis.useStoredAnalysis}")
	private boolean useStoredAnalysis;

	@Override
	public MovieAnalysisModel analyzeMovie(String imdbId) {
		MovieAnalysisModel analysisResult = null;

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
				SubtitleModel subtitle = (SubtitleModel) iterator.next();
				Sentence sentence = new Sentence(subtitle.getText().toLowerCase());

				//// find idioms
				Set<String> idiomSet = detectIdioms(sentence).getIdiomSet();
				if (!idiomSet.isEmpty()) {
					subtitle.setIdioms(idiomSet);
					idiomSubtitles.add(subtitle);
				}

				//// find phrasal verbs
				Set<String> phrasalSet = detectPhrasalVerbs(sentence).getPhrasalVerbSet();
				if (!phrasalSet.isEmpty()) {
					subtitle.setPhrasalVerbs(phrasalSet);
					phrasalVerbSubtitles.add(subtitle);

				}

//				subtitleRepository.save(subtitle);

				//// detect single words
				subtitle.setSingleWords(detectSingleWords(sentence));

				//// find phrasal verbs
				//// find adj+noun tuples

			}

			analysisResult.setImdbId(imdbId);
			analysisResult.setIdioms(idiomSubtitles);
			analysisResult.setPhrasalVerbs(phrasalVerbSubtitles);
			analysisResult.setSingleWords(singleWordSubtitles);

			movieAnalysisRepository.save(analysisResult);
//			subtitleRepository.saveAll(subtitles);
//			movieAnalysisRepository.save(analysisResult);
		}

		return analysisResult;
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
			PhrasalVerbAnalysis phrasalVerbAnalysis = detectPhrasalVerbs(nplSentence);
			subtitle.setPhrasalVerbs(phrasalVerbAnalysis.getPhrasalVerbSet());
			trace.append(phrasalVerbAnalysis.getTrace());
		}

		if (analyseSingleWords) {
			subtitle.setSingleWords(detectSingleWords(nplSentence));
		}

		subtitle.setTrace(trace.toString());
		return subtitle;
	}

	private PhrasalVerbAnalysis detectPhrasalVerbs(Sentence sentence) {
		return phrasalDetectionService.detectPhrasalVerbs(sentenceTaggingService.tagString(sentence.text()), sentence);
	}

	private IdiomAnalysis detectIdioms(Sentence sentence) {
		return idiomDetectionService.detectIdioms(sentenceTaggingService.tagString(sentence.text()), sentence);
	}

	private String detectSingleWords(Sentence sentence) {
		List<String> singleWordSet = new ArrayList<String>();
		singleWordSet.addAll(sentenceTaggingService.lemmas(sentence));
		singleWordSet.removeAll(singleWordsDetectionService.getCommonWords());

		String singleWords = "";
		singleWords = singleWordSet.stream().map(Object::toString).collect(Collectors.joining(","));

		return singleWords;
	}

}
