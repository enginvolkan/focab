package com.engin.focab.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.xmlrpc.XmlRpcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.engin.focab.jpa.MovieAnalysisModel;
import com.engin.focab.jpa.SubtitleModel;
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

	@Override
	public MovieAnalysisModel analyzeMovie(String imdbId) throws XmlRpcException {

		MovieAnalysisModel analysisResult = movieAnalysisRepository.findMovieAnalysisByImdbId(imdbId);

		if (analysisResult == null) {
			analysisResult = new MovieAnalysisModel();
			imdbId = imdbId.substring(2);
			SubtitleFile file = subtitleService.getASubtitleByImdbId(imdbId); // 0702019 0248654
			analysisResult.setFullSubtitles(file.getContentAsString("UTF-8"));
			ArrayList<SubtitleModel> subtitles = srtParserService
					.getSubtitlesFromString(analysisResult.getFullSubtitles());

			//// process each subtitle
			for (Iterator<SubtitleModel> iterator = subtitles.iterator(); iterator.hasNext();) {
				SubtitleModel subtitle = (SubtitleModel) iterator.next();
				Sentence sentence = new Sentence(subtitle.getText().toLowerCase());

				String[] taggedSentence = sentenceTaggingService.tagString(subtitle.getText());

				//// find idioms
				Set<String> idiomSet = idiomDetectionService.detectIdioms(taggedSentence, sentence);
				if (!idiomSet.isEmpty()) {
					subtitle.setIdioms(idiomSet);
				}

				//// find phrasal verbs
				Set<String> phrasalSet = phrasalDetectionService.detectPhrasalVerbs(taggedSentence, sentence);
				if (!phrasalSet.isEmpty()) {
					subtitle.setPhrasalVerbs(phrasalSet);
				}

//				subtitleRepository.save(subtitle);

				//// find phrasal verbs
				//// find adj+noun tuples
				//// detect single words

				Set<String> singleWordSet = new HashSet<String>();
				singleWordSet.addAll(sentence.lemmas());
				if (!singleWordSet.isEmpty()) {
					subtitle.setSingleWords(singleWordSet);
				}
			}
			subtitles = (ArrayList<SubtitleModel>) subtitles.stream().filter(x -> x.getIdioms() != null)
					.collect(Collectors.toList());
			analysisResult.setImdbId(imdbId);
			analysisResult.setIdioms(subtitles);
			movieAnalysisRepository.save(analysisResult);
//			subtitleRepository.saveAll(subtitles);
//			movieAnalysisRepository.save(analysisResult);
			return analysisResult;
		}

		return analysisResult;
	}

}
