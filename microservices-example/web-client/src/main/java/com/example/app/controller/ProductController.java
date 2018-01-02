package com.example.app.controller;

import com.example.app.service.ProductWebService;
import com.example.app.vo.Product;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductWebService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @HystrixCommand(fallbackMethod = "getAllFallback")
    List<Product> getAll() {
        return service.getAll();
    }

    public List<Product> getAllFallback() {
        logger.error("Error get all products!");
        return Collections.EMPTY_LIST;
    }

    @PostMapping
    @HystrixCommand
    private String addProduct(@RequestBody Product product) {
        service.addProduct(product);
        return "index-static";
    }
}
