package com.engin.focab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engin.focab.jpa.MovieAnalysisModel;

@Repository
public interface MovieAnalysisRepository extends JpaRepository<MovieAnalysisModel, String> {
}
