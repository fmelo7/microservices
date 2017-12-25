package com.example.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.service.CustomerWebService;
import com.example.app.vo.Customer;

@Controller
@RequestMapping("customers")
public class CustomerController {

	@Autowired
	private CustomerWebService customerWebService;

	@GetMapping
	ResponseEntity<List<Customer>> getAll() {
		return ResponseEntity.ok(customerWebService.getAll());
	}

}
