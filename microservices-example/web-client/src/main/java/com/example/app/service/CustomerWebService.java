package com.example.app.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.app.vo.Customer;

@Service
public class CustomerWebService {

	private static final String SERVICE_URL = "http://CUSTOMER-SERVICE/customers/api/v1";

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	public List<Customer> getAll() {
		return Arrays.asList(restTemplate.getForObject(SERVICE_URL, Customer[].class));
	}

}
