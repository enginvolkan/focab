package com.engin.focab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engin.focab.jpa.FavoriteList;

@Repository
public interface FavoriteListRepository extends JpaRepository<FavoriteList, Integer>{
	
}
