package com.example.app.customer;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceApplicationTests {

    @Autowired
    CustomerSink customerSink;

    @Autowired
    CustomerProcessor customerProcessor;

    @Autowired
    CustomerRepository repository;

    @Test
    public void contextLoads() {
        Assertions.assertThat(customerSink).isNotNull();
        Assertions.assertThat(customerProcessor).isNotNull();
        Assertions.assertThat(repository).isNotNull();
        Assertions.assertThat(new Customer()).isNotNull();
    }
}
