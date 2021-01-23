package com.engin.focab.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.engin.focab.jpa.corpus.LexiModel;

@Repository
public interface CommonWordRepository extends JpaRepository<LexiModel, Long> {
	@Query(value = "SELECT * FROM lexi as c where c.common_word_level <= ?1", nativeQuery = true)
	List<LexiModel> findAllInLevel(int level);
}