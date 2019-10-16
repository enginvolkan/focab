package com.engin.focab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engin.focab.jpa.Customer;
import com.engin.focab.jpa.FavoriteList;
import com.engin.focab.jpa.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{
	
}
