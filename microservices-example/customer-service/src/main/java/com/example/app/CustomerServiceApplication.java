package com.example.app;

import com.example.app.entity.Customer;
import com.example.app.repository.CustomerRepository;
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
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Autowired
    private CustomerRepository customerRepository;

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return new RepositoryRestConfigurerAdapter() {
            @Override
            public void configureRepositoryRestConfiguration(
                    RepositoryRestConfiguration config) {
                config.exposeIdsFor(Customer.class);
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
