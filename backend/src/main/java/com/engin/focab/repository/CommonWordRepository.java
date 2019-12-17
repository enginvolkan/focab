package com.engin.focab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engin.focab.jpa.corpus.CommonWordModel;

@Repository
public interface CommonWordRepository extends JpaRepository<CommonWordModel, Long> {

}
