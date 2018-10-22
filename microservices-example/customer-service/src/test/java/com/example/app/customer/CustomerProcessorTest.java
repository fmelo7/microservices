package com.example.app.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerProcessorTest {

    @Autowired
    private CustomerProcessor processor;

    @Test
    public void addCustomerAndReturnSavedCustomer() throws Exception {
        Customer customer = new Customer("john", "doe");
        ObjectMapper objectMapper = new ObjectMapper();
        Customer savedCustomer = processor
                .addCustomer(objectMapper.writeValueAsString(customer));
        Assertions.assertThat(savedCustomer).isNotNull();
        Assertions.assertThat(savedCustomer.getId()).isNotNull();
        Assertions.assertThat(savedCustomer.getDateCreate()).isNotNull();
        Assertions.assertThat(savedCustomer.getDateUpdate()).isNull();
        Assertions.assertThat(savedCustomer.getDateDelete()).isNull();
        Assertions.assertThat(savedCustomer.isDeleted()).isFalse();
    }
}