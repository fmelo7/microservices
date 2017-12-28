package com.example.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.app.entity.Product;
import com.example.app.repository.ProductRepository;

@SpringBootApplication
@EnableAutoConfiguration
@EnableEurekaClient
public class Application {

	@Autowired
	private ProductRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry //
						.addMapping("/api/products/v1")//
						.allowedOrigins("http://localhost:4200");
			}
		};
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			repository.save(new Product("Spring Boot", Math.random() * 1.5));
			repository.save(new Product("Spring Cloud", Math.random() * 1.5));
			repository.save(new Product("Eureka", Math.random() * 1.4));
			repository.save(new Product("Html", Math.random() * 5.0));
			repository.save(new Product("jQuery", Math.random() * 3.21));
			repository.save(new Product("Bootstrap", Math.random() * 3.32));
		};
	}
}
