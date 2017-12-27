package com.example.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.service.ProductWebService;
import com.example.app.vo.Product;

@Controller
@RequestMapping("products")
public class ProductController {

	@Autowired
	private ProductWebService microservice;

	@GetMapping
	ResponseEntity<List<Product>> getAll() {
		return ResponseEntity.ok(microservice.getAll());
	}

}
