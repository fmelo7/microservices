package com.example.app.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.SubscribableChannel;

import javax.persistence.*;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@SpringBootApplication
@EnableEurekaClient
@EnableIntegration
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    // TODO create profile dev using H2 and prod using mongodb:
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

    private CustomerRepository repository;

    @Autowired
    public CustomerProcessor(CustomerRepository repository) {
        this.repository = repository;
    }

    @ServiceActivator(inputChannel = CustomerSink.ADD_CUSTOMER)
    public Customer addCustomer(String customer) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Customer newCustomer = objectMapper.readValue(customer, Customer.class);
        return repository.save(newCustomer);
    }
}

interface CustomerSink {
    String ADD_CUSTOMER = "addCustomer";

    @Input("addCustomer")
    SubscribableChannel addCustomer();
}

@RestResource
interface CustomerRepository extends CrudRepository<Customer, UUID> {

    @Override
    default void delete(Customer entity) {
        entity.setDeleted(true);
        save(entity);
    }

    @Override
    default void delete(UUID uuid) {
        delete(findOne(uuid));
    }
}

@Entity
class Customer {

    @Id
    @GeneratedValue
    private UUID _id;

    @Column(nullable = false, length = 50)
    private String firstname;

    @Column(nullable = false, length = 50)
    private String lastname;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(insertable = false)
    private Date dateCreate;

    private Date dateUpdate;

    private Date dateDelete;

    @Column(nullable = false)
    private Boolean deleted = false;

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

    public Date getDateCreate() {
        return dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public Date getDateDelete() {
        return dateDelete;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
        this.dateDelete = onDelete(deleted);
    }

    private Date onDelete(boolean deleted) {
        return deleted ? new Date() : null;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    @PrePersist
    public void onPersist() {
        dateCreate = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        if (deleted)
            dateDelete = new Date();
        else
            dateUpdate = new Date();
    }
}
