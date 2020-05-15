package com.engin.focab.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.engin.focab.jpa.corpus.CommonWordModel;

@Repository
public interface CommonWordRepository extends JpaRepository<CommonWordModel, Long> {
	@Query(value = "SELECT * FROM commonwords as c where c.level <= ?1", nativeQuery = true)
	List<CommonWordModel> findAllInLevel(int level);
}