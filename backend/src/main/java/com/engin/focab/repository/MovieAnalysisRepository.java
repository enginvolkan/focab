package com.engin.focab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.engin.focab.jpa.MovieAnalysisModel;

@Repository
public interface MovieAnalysisRepository extends JpaRepository<MovieAnalysisModel, Long> {
	@Query(value = "SELECT DISTINCT * FROM movieanalysis as m where m.imdb_Id = ?1", nativeQuery = true)
	MovieAnalysisModel findMovieAnalysisByImdbId(String imdbId);
}
