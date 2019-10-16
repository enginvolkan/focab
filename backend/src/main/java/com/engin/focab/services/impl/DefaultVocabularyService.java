package com.engin.focab.services.impl;

import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.engin.focab.jpa.Vocabulary;
import com.engin.focab.repository.VocabularyRepository;
import com.engin.focab.services.VocabularyService;

@Component
public class DefaultVocabularyService implements VocabularyService {
	@Autowired
	private VocabularyRepository vocabularyRepository;

	@Override
	public Optional<Vocabulary> findVocabulary(String text) {
		String id = convertToID(text);
		return vocabularyRepository.findById(id);
	}

	@Override
	public Vocabulary createVocabulary(String text) {
		try {
			Vocabulary vocabulary = new Vocabulary(text);
			vocabularyRepository.save(vocabulary);
			return vocabulary;
		} catch (EntityExistsException e) {
			e.printStackTrace();
			return null;
		}

	}

	private String convertToID(String text) {
		return text.toLowerCase().trim().replaceAll("/ /","+");
	}
	
}
