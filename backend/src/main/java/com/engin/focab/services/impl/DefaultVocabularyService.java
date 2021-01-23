package com.engin.focab.services.impl;

import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.engin.focab.jpa.corpus.ExampleModel;
import com.engin.focab.jpa.corpus.LexiModel;
import com.engin.focab.repository.VocabularyRepository;
import com.engin.focab.services.VocabularyService;

@Component
public class DefaultVocabularyService implements VocabularyService {
	@Autowired
	private VocabularyRepository vocabularyRepository;

	@Override
	public Optional<LexiModel> findVocabulary(String text) {
//		String id = convertToID(text);
		return vocabularyRepository.findById(text);
	}

	@Override
	public LexiModel createVocabulary(String text) {
		try {
			LexiModel vocabulary = new LexiModel(text);
			vocabularyRepository.save(vocabulary);
			return vocabulary;
		} catch (EntityExistsException e) {
			e.printStackTrace();
			return null;
		}

	}

	private String convertToID(String text) {
		return text.toLowerCase().trim().replaceAll(" ", "+");
	}

	@Override
	public ExampleModel[] getExamples(LexiModel lexiModel) {
		return (ExampleModel[]) lexiModel.getDefinitions().stream().map(x -> x.getExamples()).toArray();
	}
}
