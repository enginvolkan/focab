package com.engin.focab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.FavoriteList;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	@Query(value="SELECT * FROM customer as c where c.email = ?1", 
			  nativeQuery = true) 
    Customer findCustomerByEmail(String email);
}
