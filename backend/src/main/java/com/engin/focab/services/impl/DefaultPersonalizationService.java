package com.engin.focab.services.impl;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.SummerizedMovieAnalysisModel;
import com.engin.focab.jpa.corpus.LexiModel;
import com.engin.focab.services.PersonalizationService;

@Component
public class DefaultPersonalizationService implements PersonalizationService {

	@Override
	public SummerizedMovieAnalysisModel personalize(SummerizedMovieAnalysisModel analysis, Customer customer) {
		Set<String> knownWords = customer.getKnownWords().stream().map(LexiModel::getText).collect(Collectors.toSet());
		analysis.setIdioms(new ArrayList<>(analysis.getIdioms().stream()
				.filter(idiom -> !knownWords.contains(idiom.getText())).collect(Collectors.toList())));
		analysis.setPhrasalVerbs(new ArrayList<>(analysis.getPhrasalVerbs().stream()
				.filter(phrasal -> !knownWords.contains(phrasal.getText())).collect(Collectors.toList())));
		analysis.setSingleWords(new ArrayList<>(analysis.getSingleWords().stream()
				.filter(word -> !knownWords.contains(word.getText())).collect(Collectors.toList())));
		return analysis;
	}

}
