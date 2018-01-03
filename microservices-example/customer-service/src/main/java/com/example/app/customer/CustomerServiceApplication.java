package com.example.app.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.SubscribableChannel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

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

@MessageEndpoint
@EnableBinding(CustomerSink.class)
class CustomerProcessor {

    @Autowired
    private CustomerRepository repository;

    @ServiceActivator(inputChannel = CustomerSink.ADD_CUSTOMER)
    public Customer addCustomer(String customer) {
        // TODO eval customer from string json to map or something else...
        return repository.save(new Customer());
    }
}

interface CustomerSink {
    String ADD_CUSTOMER = "addCustomer";

    @Input("addCustomer")
    SubscribableChannel addCustomer();
}

@RestResource
interface CustomerRepository extends JpaRepository<Customer, UUID> {
}

@Entity
class Customer {

    @Id
    @GeneratedValue
    private UUID _id;
    private String firstname;
    private String lastname;

    public Customer() {
    }

    public Customer(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public UUID getId() {
        return _id;
    }

    public void setId(UUID id) {
        this._id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
