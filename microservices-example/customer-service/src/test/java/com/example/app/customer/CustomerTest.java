package com.example.app.customer;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerTest {

    // customer table contrution
    Customer customer;

    // some date to test
    Date someDate;

    @Before
    public void setUp() throws Exception {
        customer = new Customer("john", "Doe");
        someDate = new Date();
    }

    @Test
    public void testCustomerFieldIdUUID() {
        UUID uuid = UUID.randomUUID();
        customer.setId(uuid);
        assertThat(customer.getId()).isEqualTo(uuid);
    }

    @Test
    public void testCustomerFieldFirstname() {
        // firstname String 50
        String firstname = "firstname";
        customer.setFirstname(firstname);
        assertThat(customer.getFirstname()).isEqualTo(firstname);
    }

    @Test
    public void testCustomerFieldLastname() {
        // lastname String 50
        String lastname = "lastname";
        customer.setLastname(lastname);
        assertThat(customer.getLastname()).isEqualTo(lastname);
    }

    @Test
    public void CustomerDeleted() {
        // deleted boolean flag of register deleted
        boolean someBoolean = Math.random() < 0.5;
        customer.setDeleted(someBoolean);
        assertThat(customer.isDeleted()).isEqualTo(someBoolean);
    }

    @Test
    public void CustomerIsDeletedThenDateDeleteMustHaveValue() {
        // deleted boolean flag of register deleted
        boolean someBoolean = true;
        customer.setDeleted(someBoolean);
        assertThat(customer.isDeleted()).isEqualTo(someBoolean);
        assertThat(customer.getDateDelete()).isEqualTo(someDate);
    }

    @Test
    public void CustomerIsNotDeletedThenDateDeleteMustNotHaveValue() {
        // deleted boolean flag of register deleted
        boolean someBoolean = false;
        customer.setDeleted(someBoolean);
        assertThat(customer.isDeleted()).isEqualTo(someBoolean);
        assertThat(customer.getDateDelete()).isEqualTo(null);
    }

    @Test
    public void testCustomerFields() {

        // documents set of
        // TODO

        // addresses list of address
        // TODO

        // phones list of phone
        // TODO

        // comments list of comments
        // TODO
    }
}