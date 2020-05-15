package com.engin.focab.services.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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

//	@Autowired
//	private SentenceTaggingService sentenceTaggingService;

	@Value("${movie.analysis.useStoredAnalysis}")
	private boolean useStoredAnalysis;

	@Override
	public SummerizedMovieAnalysisModel analyzeMovie(String imdbId) {
		MovieAnalysisModel analysisResult = null;

		Set<String> singleWords = new HashSet<>();
		Map<String, String> singleWordsMap = new HashMap<String, String>();

		HashMap<String, List<String>> aggregatedIdiomMap = new HashMap<String, List<String>>();
		HashMap<String, List<String>> aggregatedPhrasalverbMap = new HashMap<String, List<String>>();
		HashMap<String, List<String>> aggregatedSingleWordMap = new HashMap<String, List<String>>();

		Long idiomTotalDuration = 0L;
		Long phrasalTotalDuration = 0L;
		Long idiomAverageDuration = 0L;
		Long phrasalAverageDuration = 0L;
		Long singleAverageDuration = 0L;
		Long singleTotalDuration = 0L;
		Long singleMassProcessingDuration = 0L;

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

			Properties props = new Properties();
			props.setProperty("pos.model", "english-left3words-distsim.tagger");
			props.setProperty("ner.model",
					"english.all.3class.distsim.crf.ser.gz,english.conll.4class.distsim.crf.ser.gz,english.muc.7class.distsim.crf.ser.gz");
			props.setProperty("ner.useSUTime", "false");
			props.setProperty("ner.applyFineGrained", "false");

			//// process each subtitle
			for (int i = 0; i < subtitles.size(); i++) {
				SubtitleModel subtitle = subtitles.get(i);
				Sentence sentence = new Sentence(subtitle.getText());
				List<String> sentenceLemmas = sentence.lemmas(props);

				//// find idioms
				Instant idiomStart = Instant.now();
				Set<String> idiomSet = idiomDetectionService.detectIdioms(sentence, sentenceLemmas).getIdiomSet();
				if (!idiomSet.isEmpty()) {
					subtitle.setIdioms(idiomSet);
					idiomSubtitles.add(subtitle);
					for (String idiom : idiomSet) {
						addToMap(aggregatedIdiomMap, idiom, sentence.text());
					}

				}
				Instant idiomEnd = Instant.now();

				//// find phrasal verbs
				Instant phrasalStart = Instant.now();
				List<String> phrasalSet = phrasalDetectionService.detectPhrasalVerbs(sentence, sentenceLemmas);
				if (!phrasalSet.isEmpty()) {
					subtitle.setPhrasalVerbs(phrasalSet);
					phrasalVerbSubtitles.add(subtitle);
					for (String phrasal : phrasalSet) {
						addToMap(aggregatedPhrasalverbMap, phrasal, sentence.text());
					}
				}
				Instant phrasalEnd = Instant.now();

				subtitleRepository.save(subtitle);

				//// detect single words
				Instant singleStart = Instant.now();
				final int subtitleIndex = i;

				List<String> namedEntities = sentence.nerTags(props);
				for (int j = 0; j < sentenceLemmas.size(); j++) {
					if(namedEntities.get(j).equals("O")) {
						singleWordsMap.merge(sentenceLemmas.get(j).toLowerCase(Locale.ENGLISH), " " + subtitleIndex,
								String::concat);
				}else {
						System.out.println(
								"Named Entity disregarded: " + sentenceLemmas.get(j) + " | " + namedEntities.get(j));
				}
					}

				Instant singleEnd = Instant.now();

				//// find phrasal verbs
				//// find adj+noun tuples
				Duration idiomDuration = Duration.between(idiomStart, idiomEnd);
				Duration phrasalDuration = Duration.between(phrasalStart, phrasalEnd);
				Duration singleDuration = Duration.between(singleStart, singleEnd);

				idiomTotalDuration = idiomTotalDuration + idiomDuration.toMillis();
				phrasalTotalDuration = phrasalTotalDuration + phrasalDuration.toMillis();
				singleTotalDuration = singleTotalDuration + singleDuration.toMillis();

			}

			// process single words set
			Instant singleMassStart= Instant.now();

			singleWords.addAll(singleWordsDetectionService.reduce(singleWordsMap.keySet()));
			if (!singleWords.isEmpty()) {
				for (String singleWord : singleWords) {
					String[] subtitleIds = singleWordsMap.get(singleWord).split(" ");
					for (int i = 0; i < subtitleIds.length; i++) {
						if (!subtitleIds[i].isBlank()) {
							addToMap(aggregatedSingleWordMap, singleWord,
									subtitles.get(Integer.valueOf(subtitleIds[i]).intValue()).getText());
						}
					}
				}
			}

			Instant singleMassEnd = Instant.now();


			idiomAverageDuration = idiomTotalDuration / subtitles.size();
			phrasalAverageDuration = phrasalTotalDuration / subtitles.size();
			singleAverageDuration = singleTotalDuration / subtitles.size();
			singleMassProcessingDuration = Duration.between(singleMassStart, singleMassEnd).toSeconds();

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
		summerizedAnalysis.setIdiomAverageDuration(idiomAverageDuration);
		summerizedAnalysis.setPhrasalAverageDuration(phrasalAverageDuration);
		summerizedAnalysis.setSingleAverageDuration(singleAverageDuration);
		summerizedAnalysis.setSingleMassDuration(singleMassProcessingDuration);

		return summerizedAnalysis;
	}

	@Override
	public SubtitleModel analyzeSentence(String sentence, boolean analyseIdioms, boolean analysePhrasalVerbs,
			boolean analyseSingleWords) {
		SubtitleModel subtitle = new SubtitleModel(sentence);
		Sentence nplSentence = new Sentence(sentence);
		StringBuilder trace = new StringBuilder();
		Properties props = new Properties();
		props.setProperty("pos.model", "english-left3words-distsim.tagger");
		List<String> sentenceLemmas = nplSentence.lemmas(props);

		if (analyseIdioms) {
			IdiomAnalysis idiomAnalysis = idiomDetectionService.detectIdioms(nplSentence, sentenceLemmas);
			subtitle.setIdioms(idiomAnalysis.getIdiomSet());
			trace.append(idiomAnalysis.getTrace());
		}

		if (analysePhrasalVerbs) {
			List<String> phrasalVerbAnalysis = phrasalDetectionService.detectPhrasalVerbs(nplSentence, sentenceLemmas);
			subtitle.setPhrasalVerbs(phrasalVerbAnalysis);
			// trace.append(phrasalVerbAnalysis.getTrace());
		}

		if (analyseSingleWords) {
			subtitle.setSingleWords(
					String.join(",", singleWordsDetectionService.reduce(new HashSet<String>(sentenceLemmas))));
		}

		subtitle.setTrace(trace.toString());
		return subtitle;
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

}
