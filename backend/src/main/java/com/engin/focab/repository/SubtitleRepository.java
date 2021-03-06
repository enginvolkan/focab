package com.engin.focab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engin.focab.jpa.SubtitleModel;

@Repository
public interface SubtitleRepository extends JpaRepository<SubtitleModel, Integer> {
}
