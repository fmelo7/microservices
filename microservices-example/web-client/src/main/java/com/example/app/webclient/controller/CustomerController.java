package com.example.app.webclient.controller;

import com.example.app.webclient.service.CustomerWebService;
import com.example.app.webclient.vo.Customer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@CrossOrigin({"http://localhost:4200"})
public class CustomerController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerWebService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @HystrixCommand
    List<Customer> getAll() {
        return service.getAll();
    }

    @PostMapping
    @HystrixCommand
    public String addCustomer(@RequestBody Customer customer) {
        if (service.addCustomer(customer)){
            return "index-static";
        }
        // TODO error message for add object on rabbitmq
        return "error";
    }

    // TODO others methods restfull API
}
