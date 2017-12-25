package com.example.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.app.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}