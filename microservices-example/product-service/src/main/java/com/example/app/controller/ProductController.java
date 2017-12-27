package com.example.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.entity.Product;
import com.example.app.repository.ProductRepository;

@Controller
@RequestMapping("products/api/v1")
public class ProductController {

	@Autowired
	private ProductRepository repository;

	@GetMapping
	public ResponseEntity<List<Product>> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}

	// TODO others methods
}
