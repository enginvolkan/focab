package com.engin.focab.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.engin.focab.jpa.FavoriteEntry;
import com.engin.focab.jpa.FavoriteList;

@Repository
public interface FavoriteListRepository extends JpaRepository<FavoriteList, Integer>{
	@Query(value = "SELECT * FROM favorite_list as f where f.customer_username = ?1 and f.name = ?2",
			  nativeQuery = true)
	FavoriteList findFavoriteListByCustomer(String username, String name);

	@Query(value="SELECT * FROM favorite_list_favorite_entries as f where f.favorite_list_favorite_id = ?1",
			  nativeQuery = true)
	ArrayList<FavoriteEntry> findFavoritesByList(Integer integer);
}
