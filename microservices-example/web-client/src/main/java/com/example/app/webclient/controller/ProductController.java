package com.example.app.webclient.controller;

import com.example.app.webclient.service.ProductWebService;
import com.example.app.webclient.vo.Product;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin({"http://localhost:4200"})
public class ProductController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductWebService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @HystrixCommand
    List<Product> getAll() {
        return service.getAll();
    }

    @PostMapping
    @HystrixCommand
    private String addProduct(@RequestBody Product product) {
        service.addProduct(product);
        return "index-static";
    }
}
