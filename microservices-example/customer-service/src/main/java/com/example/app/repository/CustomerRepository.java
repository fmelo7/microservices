package com.example.app.repository;

import com.example.app.entity.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource
public interface CustomerRepository extends PagingAndSortingRepository<Customer, UUID> {

}