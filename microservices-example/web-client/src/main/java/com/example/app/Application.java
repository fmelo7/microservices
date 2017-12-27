package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAutoConfiguration
//@EnableDiscoveryClient
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@LoadBalanced
	@Bean
	protected RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
