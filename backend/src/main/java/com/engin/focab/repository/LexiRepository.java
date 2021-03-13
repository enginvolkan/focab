package com.engin.focab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.engin.focab.jpa.corpus.LexiModel;

@Repository
public interface LexiRepository extends JpaRepository<LexiModel, String>{
	@Query(value = "SELECT DISTINCT * FROM lexi as l where l.text = ?1", nativeQuery = true)
	LexiModel findLexiByText(String text);
}
