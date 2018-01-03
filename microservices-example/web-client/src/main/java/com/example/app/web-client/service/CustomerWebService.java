package com.example.app.customer.service;

import com.example.app.customer.messaging.CustomerSource;
import com.example.app.customer.vo.Customer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableBinding(CustomerSource.class)
public class CustomerWebService {

    public static String SERVICE_URL = "http://CUSTOMER-SERVICE/customers";

    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    @Autowired
    private CustomerSource source;

    public List<Customer> getAllFallback() {
        return Collections.emptyList();
    }

    //@HystrixCommand(fallbackMethod = "getAllFallback")
    public List<Customer> getAll() {
        return restTemplate
                .exchange(
                        SERVICE_URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Resources<Customer>>() {
                        })
                .getBody()
                .getContent()
                .stream()
                .collect(Collectors.toList());
    }

    @HystrixCommand
    public void addCustomer(Customer customer) {
        source.addCustomer().send(MessageBuilder.withPayload(customer.toString()).build());
    }
}
