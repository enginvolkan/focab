package com.engin.focab.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.engin.focab.jpa.corpus.IdiomModel;

@Repository
public interface IdiomRepository extends JpaRepository<IdiomModel, String>{
	
	@Query(value="SELECT * FROM idiommodel as i where i.idiom LIKE %?1%", 
			  nativeQuery = true) 
	IdiomModel[] findSimilarIdioms(String word);
}
