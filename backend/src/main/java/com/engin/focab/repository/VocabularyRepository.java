package com.engin.focab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engin.focab.jpa.Vocabulary;

@Repository
public interface VocabularyRepository extends JpaRepository<Vocabulary, String>{
	
}