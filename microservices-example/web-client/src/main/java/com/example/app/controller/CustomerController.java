package com.example.app.controller;

import com.example.app.service.CustomerWebService;
import com.example.app.vo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerWebService service;

    @PostMapping
    private String addCustomer(@RequestBody Customer customer) {
        service.addCustomer(customer);
        return "index-static";
    }

    @GetMapping
    ResponseEntity<List<Customer>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

}
