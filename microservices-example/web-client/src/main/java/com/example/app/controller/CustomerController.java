package com.example.app.controller;

import com.example.app.service.CustomerWebService;
import com.example.app.vo.Customer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerWebService service;

    @PostMapping
    @HystrixCommand
    private String addCustomer(@RequestBody Customer customer) {
        service.addCustomer(customer);
        return "index-static";
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @HystrixCommand(fallbackMethod = "getAllFallback")
    List<Customer> getAll() {
        return service.getAll();
    }

    public List<Customer> getAllFallback() {
        logger.error("Error get all customers!");
        return Collections.EMPTY_LIST;
    }

    // TODO others methods restfull API
}
