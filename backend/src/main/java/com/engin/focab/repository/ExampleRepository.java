package com.engin.focab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.FavoriteList;
import com.engin.focab.jpa.corpus.ExampleModel;

@Repository
public interface ExampleRepository extends JpaRepository<ExampleModel, Long>{
}
