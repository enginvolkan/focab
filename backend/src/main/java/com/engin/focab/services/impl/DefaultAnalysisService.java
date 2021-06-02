package com.engin.focab.services.impl;

import java.net.MalformedURLException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.xmlrpc.XmlRpcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.engin.focab.constants.FocabConstants;
import com.engin.focab.dtos.LexiDto;
import com.engin.focab.exceptions.OpenSubtitlesException;
import com.engin.focab.jpa.IdiomAnalysis;
import com.engin.focab.jpa.MovieAnalysisModel;
import com.engin.focab.jpa.SubtitleModel;
import com.engin.focab.jpa.SummerizedMovieAnalysisModel;
import com.engin.focab.repository.LexiRepository;
import com.engin.focab.repository.MovieAnalysisRepository;
import com.engin.focab.repository.SubtitleRepository;
import com.engin.focab.services.AnalysisService;
import com.engin.focab.services.IndexedSearchService;
import com.engin.focab.services.PhrasalVerbsDetectionService;
import com.engin.focab.services.SrtParserService;
import com.engin.focab.services.SubtitleService;
import com.github.wtekiela.opensub4j.response.SubtitleFile;
import com.google.common.collect.Iterables;

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
	private LexiRepository lexiRepository;

	@Autowired
	@Qualifier("constituencyParser")
	private PhrasalVerbsDetectionService phrasalDetectionService;

	@Autowired
	private SingleWordDetectionService singleWordsDetectionService;

//	@Autowired
//	private SentenceTaggingService sentenceTaggingService;

	@Value("${movie.analysis.useStoredAnalysis}")
	private boolean useStoredAnalysis;
	@Value("${movie.analysis.useStoredSubtitles}")
	private boolean useStoredSubtitles;
	@Value("${movie.analysis.saveEmptySubtitles}")
	private boolean saveEmptySubtitles;
	@Autowired
	private IndexedSearchService indexedSearchService;

	@Override
	public SummerizedMovieAnalysisModel analyzeMovie(String imdbId) throws MalformedURLException, XmlRpcException {
		MovieAnalysisModel analysisResult = null;

		Long lemmaTotalDuration = 0L;
		Long idiomTotalDuration = 0L;
		Long phrasalTotalDuration = 0L;
		Long singleTotalDuration = 0L;
		Long aggregationDuration = 0L;
		Long downloadDuration = 0L;
		Long dbsaveDuration = 0L;

		Instant dbStart = Instant.EPOCH;
		Instant dbEnd = Instant.EPOCH;
		Instant downloadStart = Instant.EPOCH;
		Instant downloadEnd = Instant.EPOCH;
		Instant lemmaStart;
		Instant lemmaEnd;
		Instant idiomStart;
		Instant idiomEnd;
		Instant phrasalStart;
		Instant phrasalEnd;
		Instant singleStart;
		Instant singleEnd;
		Instant aggregationStart;
		Instant aggregationEnd;
		Instant dbsaveStart;
		Instant dbsaveEnd;

		Instant batchSphinxStart;
		Instant batchSphinxEnd;

		Duration lemmaDuration;
		Duration idiomDuration;
		Duration phrasalDuration;
		Duration singleDuration;

		dbStart = Instant.now();
		System.out.println("Start:" + dbStart.toString());

		imdbId = imdbId.substring(2);
		Optional<MovieAnalysisModel> savedAnalysis = movieAnalysisRepository.findById(imdbId);
		analysisResult = canUseSavedAnalysis(savedAnalysis) ? savedAnalysis.get() : new MovieAnalysisModel(imdbId);
		dbEnd = Instant.now();

		if (!canUseSavedAnalysis(savedAnalysis)) {
			if (canUseSavedSubtitles(savedAnalysis)) {
				analysisResult.setFullSubtitles(savedAnalysis.get().getFullSubtitles());
			} else {

				downloadStart = Instant.now();

				SubtitleFile file = subtitleService.getASubtitleByImdbId(imdbId); // 0702019 0248654

				if (file == null) {
					throw new OpenSubtitlesException("OpenSubtitles.org is not reachable");
				}
				analysisResult.setFullSubtitles(file.getContentAsString("UTF-8"));
				downloadEnd = Instant.now();

			}
			ArrayList<SubtitleModel> subtitles = srtParserService
					.getSubtitlesFromString(analysisResult.getFullSubtitles());
			ArrayList<SubtitleModel> idiomSubtitles = new ArrayList<SubtitleModel>();
			ArrayList<SubtitleModel> phrasalVerbSubtitles = new ArrayList<SubtitleModel>();
			ArrayList<SubtitleModel> singleWordSubtitles = new ArrayList<SubtitleModel>();
			ArrayList<SubtitleModel> emptySubtitles = new ArrayList<SubtitleModel>();

			Properties props = new Properties();
			props.setProperty("pos.model", "english-left3words-distsim.tagger");
			System.out.println("Download end:" + downloadEnd.toString());
			// try to search for a larger idiom set for all sentences at once
			batchSphinxStart = Instant.now();
			allAtOnce(subtitles, props);
			batchSphinxEnd = Instant.now();
			System.out
					.println("Batch Sphinx Duration:" + Duration.between(batchSphinxStart, batchSphinxEnd).toMillis());
			//// process each subtitle
			for (int i = 0; i < subtitles.size(); i++) {
				lemmaStart = Instant.now();

				SubtitleModel subtitle = subtitles.get(i);
				Sentence sentence = new Sentence(subtitle.getText());
				List<String> sentenceLemmas = sentence.lemmas(props);
				lemmaEnd = Instant.now();

				//// find idioms
				idiomStart = Instant.now();
				Set<String> idiomSet = idiomDetectionService.detectIdioms(sentence, sentenceLemmas).getIdiomSet();
				if (!idiomSet.isEmpty()) {
					subtitle.setIdioms(idiomSet);
					idiomSubtitles.add(subtitle);
				}
				idiomEnd = Instant.now();

				//// find phrasal verbs
				phrasalStart = Instant.now();
				List<String> phrasalSet = phrasalDetectionService.detectPhrasalVerbs(sentence, sentenceLemmas);
				if (!phrasalSet.isEmpty()) {
					subtitle.setPhrasalVerbs(phrasalSet);
					phrasalVerbSubtitles.add(subtitle);
				}
				phrasalEnd = Instant.now();

				//// detect single words
				singleStart = Instant.now();
				String singleWords = singleWordsDetectionService.findSingleWords(sentence, sentenceLemmas);
				if (!StringUtils.isBlank(singleWords)) {
					subtitle.setSingleWords(singleWords);
					singleWordSubtitles.add(subtitle);
				}
				singleEnd = Instant.now();

				if (saveEmptySubtitles && CollectionUtils.isEmpty(subtitle.getIdioms())
						&& CollectionUtils.isEmpty(subtitle.getPhrasalVerbs())
						&& StringUtils.isBlank(subtitle.getSingleWords())) {
					emptySubtitles.add(subtitle);
				}
				// subtitleRepository.save(subtitle);

				//// find phrasal verbs
				//// find adj+noun tuples
				idiomDuration = Duration.between(idiomStart, idiomEnd);
				phrasalDuration = Duration.between(phrasalStart, phrasalEnd);
				singleDuration = Duration.between(singleStart, singleEnd);
				lemmaDuration = Duration.between(lemmaStart, lemmaEnd);

				lemmaTotalDuration = lemmaTotalDuration + lemmaDuration.toMillis();
				idiomTotalDuration = idiomTotalDuration + idiomDuration.toMillis();
				phrasalTotalDuration = phrasalTotalDuration + phrasalDuration.toMillis();
				singleTotalDuration = singleTotalDuration + singleDuration.toMillis();

			}
			dbsaveStart = Instant.now();
			analysisResult.setIdioms(idiomSubtitles);
			analysisResult.setPhrasalVerbs(phrasalVerbSubtitles);
			analysisResult.setSingleWords(singleWordSubtitles);
			analysisResult.setEmptySubtitles(emptySubtitles);
			subtitleRepository.saveAll(idiomSubtitles);
			subtitleRepository.saveAll(phrasalVerbSubtitles);
			subtitleRepository.saveAll(singleWordSubtitles);
			subtitleRepository.saveAll(emptySubtitles);
			movieAnalysisRepository.save(analysisResult);

			dbsaveEnd = Instant.now();
			dbsaveDuration = Duration.between(dbsaveStart, dbsaveEnd).toMillis();

			downloadDuration = Duration.between(downloadStart, downloadEnd).toMillis()
					+ Duration.between(dbStart, dbEnd).toMillis();

		}
		aggregationStart = Instant.now();
		SummerizedMovieAnalysisModel summerizedAnalysis = summarize(analysisResult);
		aggregationEnd = Instant.now();
		aggregationDuration = Duration.between(aggregationStart, aggregationEnd).toMillis();

		summerizedAnalysis.setLemmaTotalDuration(lemmaTotalDuration);
		summerizedAnalysis.setIdiomTotalDuration(idiomTotalDuration);
		summerizedAnalysis.setPhrasalTotalDuration(phrasalTotalDuration);
		summerizedAnalysis.setSingleTotalDuration(singleTotalDuration);
		summerizedAnalysis.setAggregationDuration(aggregationDuration);
		summerizedAnalysis.setDownloadDuration(downloadDuration);
		summerizedAnalysis.setDbsaveDuration(dbsaveDuration);

		System.out.println("End:" + Instant.now().toString());

		return summerizedAnalysis;
	}

	private boolean canUseSavedSubtitles(Optional<MovieAnalysisModel> savedAnalysis) {
		return useStoredSubtitles && savedAnalysis.isPresent();
	}

	private boolean canUseSavedAnalysis(Optional<MovieAnalysisModel> savedAnalysis) {
		return useStoredAnalysis && savedAnalysis.isPresent();
	}

	private void allAtOnce(ArrayList<SubtitleModel> subtitles, Properties props) {
		HashSet<String> foundSet = new HashSet<>();
		HashSet<String> allLemmas = new HashSet<>();
		HashMap<String, String> idiomsAndRegex = new HashMap<>();
		List<String> allSentencesInLemmas = new ArrayList<>();

		for (int i = 0; i < subtitles.size(); i++) {
			SubtitleModel subtitle = subtitles.get(i);
			Sentence sentence = new Sentence(subtitle.getText().toLowerCase());
			List<String> lemmas = sentence.lemmas(props);
			allSentencesInLemmas.add(String.join(" ", lemmas));
			allLemmas.addAll(lemmas);
		}
		Iterable<List<String>> chunkList = Iterables.partition(allLemmas, 50);
		for (List<String> list : chunkList) {
			String allWords = list.stream()
					.filter(x -> !FocabConstants.WORDS_TO_SKIP.contains(x)
							&& Pattern.compile(FocabConstants.WORD_REGEX_PATTERN).matcher(x).matches())
					.collect(Collectors.joining("|"));
			if (allWords.length() > 1) {
				// Find a larger set for all possible idioms
				idiomsAndRegex.putAll(indexedSearchService.findIdiomsByWordWithRegex(allWords));
			}
		}

		for (Iterator<String> iterator = allSentencesInLemmas.iterator(); iterator.hasNext();) {
			String sentence = iterator.next();
			idiomsAndRegex.forEach((k, v) -> {
				if (sentence.matches(v)) {
					foundSet.add(k);
				}
			});
		}

		System.out.println(foundSet.size() + " idioms found!");

	}

	private SummerizedMovieAnalysisModel summarize(MovieAnalysisModel analysisResult) {

		SummerizedMovieAnalysisModel summerizedAnalysis = new SummerizedMovieAnalysisModel(analysisResult.getImdbId());
		HashMap<String, List<String>> aggregatedIdiomMap = new HashMap<>();
		HashMap<String, List<String>> aggregatedPhrasalverbMap = new HashMap<>();
		HashMap<String, List<String>> aggregatedSingleWordMap = new HashMap<>();

		for (SubtitleModel subtitle : analysisResult.getIdioms()) {
			for (String idiom : subtitle.getIdioms()) {
				addToMap(aggregatedIdiomMap, idiom, subtitle.getText());
			}
		}

		for (SubtitleModel subtitle : analysisResult.getPhrasalVerbs()) {
			for (String phrasal : subtitle.getPhrasalVerbs()) {
				addToMap(aggregatedPhrasalverbMap, phrasal, subtitle.getText());
			}
		}

		for (SubtitleModel subtitle : analysisResult.getSingleWords()) {
			for (String single : subtitle.getSingleWords().split(" ")) {
				addToMap(aggregatedSingleWordMap, single, subtitle.getText());
			}
		}

		summerizedAnalysis.setIdioms(convertMapKeysToDto(aggregatedIdiomMap));
		summerizedAnalysis.setPhrasalVerbs(convertMapKeysToDto(aggregatedPhrasalverbMap));
		summerizedAnalysis.setSingleWords(convertMapKeysToDto(aggregatedSingleWordMap));

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

	private ArrayList<LexiDto> convertMapKeysToDto(HashMap<String, List<String>> initialMap) {
		ArrayList<LexiDto> lexis = new ArrayList<>();
		for (Map.Entry<String, List<String>> entry : initialMap.entrySet()) {
			String text = entry.getKey();
			ArrayList<String> movieExamples = (ArrayList<String>) entry.getValue();
			LexiDto lexi = new LexiDto(text);
			lexi.setMovieExamples(movieExamples);
			lexis.add(lexi);
		}
		return lexis;

	}

	@Override
	public List<SubtitleModel> getFullSubtitles(String imdbId) {
		imdbId = imdbId.substring(2);
		Optional<MovieAnalysisModel> savedAnalysis = movieAnalysisRepository.findById(imdbId);
		Set<SubtitleModel> fullSubtitles = new HashSet<>();
		if (savedAnalysis.isPresent()) {
			fullSubtitles.addAll(savedAnalysis.get().getIdioms());
			fullSubtitles.addAll(savedAnalysis.get().getPhrasalVerbs());
			fullSubtitles.addAll(savedAnalysis.get().getSingleWords());
			fullSubtitles.addAll(savedAnalysis.get().getEmptySubtitles());
			List<SubtitleModel> result = fullSubtitles.stream().sorted(Comparator.comparing(SubtitleModel::getId))
					.collect(Collectors.toList());
			return result;
		}
		return List.of(new SubtitleModel());
	}

}
