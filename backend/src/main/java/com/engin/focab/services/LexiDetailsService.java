package com.engin.focab.services;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.engin.focab.jpa.corpus.ExampleModel;
import com.engin.focab.jpa.corpus.LexiModel;

@Component
public interface LexiDetailsService {
	public LexiModel createVocabulary(String text);
	public Optional<LexiModel> findLexi(String text);
	public ExampleModel[] getExamples(LexiModel lexiModel);
}
