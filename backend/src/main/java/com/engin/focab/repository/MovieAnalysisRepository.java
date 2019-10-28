package com.engin.focab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.FavoriteList;
import com.engin.focab.jpa.MovieAnalysisModel;

@Repository
public interface MovieAnalysisRepository extends JpaRepository<MovieAnalysisModel, Long>{
	@Query(value="SELECT SINGLE * FROM movieanalysis as m where m.imdbId = ?1", 
			  nativeQuery = true) 
    MovieAnalysisModel findMovieAnalysisByImdbId(String imdbId);
}
