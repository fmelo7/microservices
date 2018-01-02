package com.example.app.messaging;

import com.example.app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.MessageEndpoint;

@MessageEndpoint
@EnableBinding(CustomerSink.class)
public class CustomerProcessor {

    @Autowired
    private CustomerRepository repository;

    @StreamListener(CustomerSink.ADD_CUSTOMER)
    public void addCustomer(String customer) {
        System.out.println("customer: " + customer);
        // TODO repository.save(new Customer(customer));
    }

}
