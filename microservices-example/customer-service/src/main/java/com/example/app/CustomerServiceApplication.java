package com.example.app;

import com.example.app.entity.Customer;
import com.example.app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableEurekaClient
@EnableIntegration
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Autowired
    private CustomerRepository customerRepository;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry //
                        .addMapping("/api/v1/customers")//
                        .allowedOrigins("http://localhost:4200");
            }
        };
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            customerRepository.save(new Customer("john", "Doe"));
            customerRepository.save(new Customer("jose", "silva"));
            customerRepository.save(new Customer("paul", "smith"));
            customerRepository.save(new Customer("ringo", "star"));
            customerRepository.save(new Customer("simon", "garf"));
            customerRepository.save(new Customer("peter", "framp"));
        };
    }
}
