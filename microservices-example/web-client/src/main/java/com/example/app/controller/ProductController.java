package com.example.app.controller;

import com.example.app.service.ProductWebService;
import com.example.app.vo.Customer;
import com.example.app.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductWebService service;

    @GetMapping
    ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    private String addProduct(@RequestBody Product product) {
        service.addProduct(product);
        return "index-static";
    }


}
