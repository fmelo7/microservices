package com.example.app;

import com.example.app.entity.Product;
import com.example.app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableEurekaClient
@EnableIntegration
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Autowired
    private ProductRepository repository;

    @Bean
    RepositoryRestConfigurer repositoryRestConfigurer() {
        return new RepositoryRestConfigurerAdapter() {
            @Override
            public void configureRepositoryRestConfiguration(
                    RepositoryRestConfiguration config) {
                config.exposeIdsFor(Product.class);
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
