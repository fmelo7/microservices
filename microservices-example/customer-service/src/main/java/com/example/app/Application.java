package com.example.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.app.entity.Customer;
import com.example.app.repository.CustomerRepository;

@SpringBootApplication
@EnableAutoConfiguration
public class Application {

	@Autowired
	private CustomerRepository customerRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			customerRepository.save(new Customer());
			customerRepository.save(new Customer());
			customerRepository.save(new Customer());
			customerRepository.save(new Customer());
			customerRepository.save(new Customer());
			customerRepository.save(new Customer());
		};
	}
}
