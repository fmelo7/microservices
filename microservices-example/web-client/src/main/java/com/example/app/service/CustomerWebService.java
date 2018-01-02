package com.example.app.service;

import com.example.app.messaging.CustomerSource;
import com.example.app.vo.Customer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@EnableBinding(CustomerSource.class)
public class CustomerWebService {

    private static String SERVICE_URL = "http://CUSTOMER-SERVICE/api/v1/customers";

    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    @Autowired
    private CustomerSource source;

    public List<Customer> getAllFallback() {
        return Collections.emptyList();
    }

    @HystrixCommand(fallbackMethod = "getAllFallback")
    public List<Customer> getAll() {
        return Arrays.asList(restTemplate.getForObject(SERVICE_URL, Customer[].class));
    }

    public void addCustomer(Customer customer) {
        source.addCustomer().send(MessageBuilder.withPayload(customer.toString()).build());
    }

}
