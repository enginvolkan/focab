package com.engin.focab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engin.focab.jpa.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
