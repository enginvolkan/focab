package com.engin.focab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engin.focab.jpa.corpus.LexiModel;

@Repository
public interface VocabularyRepository extends JpaRepository<LexiModel, String>{
	
}
