package com.engin.focab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.engin.focab.jpa.corpus.LexiModel;

@Repository
public interface IdiomRepository extends JpaRepository<LexiModel, String> {

	@Query(value = "SELECT * FROM lexi as i where i.text LIKE %?1% AND type = 'IDIOM'",
			  nativeQuery = true)
	LexiModel[] findSimilarIdioms(String word);

	@Query(value = "SELECT * FROM lexi where regex is null AND type = 'IDIOM'", nativeQuery = true)
	LexiModel[] findNullRegex();
}
