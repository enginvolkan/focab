package com.engin.focab.services;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.engin.focab.jpa.Vocabulary;

@Component
public interface VocabularyService {
	public Vocabulary createVocabulary(String text);
	public Optional<Vocabulary> findVocabulary(String text);

}
