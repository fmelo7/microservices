package com.example.app.customer;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    private Customer customer;

    @Before
    public void setUp() throws Exception {
        customer = new Customer("john", "doe");
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testRepositoryCannotSaveWithNullValues() throws Exception {
        Customer savedCustomer = repository.save(new Customer());
    }

    @Test
    public void testRepositoryCanSaveCustomer() throws Exception {
        Customer savedCustomer = repository.save(customer);
        Assertions.assertThat(savedCustomer).isNotNull();
        Assertions.assertThat(savedCustomer.getId()).isNotNull();
        Assertions.assertThat(savedCustomer.getDateCreate()).isNotNull();
        Assertions.assertThat(savedCustomer.getDateUpdate()).isNull();
        Assertions.assertThat(savedCustomer.getDateDelete()).isNull();
        Assertions.assertThat(savedCustomer.isDeleted()).isFalse();
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testRepositoryCannotUpdateFirstnameToNullValues() throws Exception {
        Customer updatedCustomer = repository.save(customer);
        updatedCustomer.setFirstname(null);
        repository.save(updatedCustomer);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testRepositoryCannotUpdateLastnameToNullValues() throws Exception {
        Customer updatedCustomer = repository.save(customer);
        updatedCustomer.setLastname(null);
        repository.save(updatedCustomer);
    }

    @Test
    public void testRepositoryCanUpdateFirstnameCustomer() throws Exception {
        Customer savedCustomer = repository.save(customer);
        savedCustomer.setFirstname("frank");
        savedCustomer = repository.save(savedCustomer);
        Customer updatedCustomer = repository.findOne(savedCustomer.getId());
        Assertions.assertThat(updatedCustomer).isNotNull();
        Assertions.assertThat(updatedCustomer.getId()).isEqualTo(savedCustomer.getId());
        Assertions.assertThat(updatedCustomer.getDateCreate()).isNotNull();
        Assertions.assertThat(updatedCustomer.getDateUpdate()).isNotNull();
        Assertions.assertThat(updatedCustomer.getDateDelete()).isNull();
        Assertions.assertThat(updatedCustomer.isDeleted()).isFalse();
    }

    @Test
    public void testRepositoryCanUpdateLastnameCustomer() throws Exception {
        Customer savedCustomer = repository.save(customer);
        savedCustomer.setLastname("smith");
        savedCustomer = repository.save(savedCustomer);
        Customer updatedCustomer = repository.findOne(savedCustomer.getId());
        Assertions.assertThat(updatedCustomer).isNotNull();
        Assertions.assertThat(updatedCustomer.getId()).isEqualTo(savedCustomer.getId());
        Assertions.assertThat(updatedCustomer.getDateCreate()).isNotNull();
        Assertions.assertThat(updatedCustomer.getDateUpdate()).isNotNull();
        Assertions.assertThat(updatedCustomer.getDateDelete()).isNull();
        Assertions.assertThat(updatedCustomer.isDeleted()).isFalse();
    }

    @Test
    public void testRepositoryDeleteEntityUpdateDeletedToTrueAndDateDeleteToNewDate() {
        Customer savedCustomer = repository.save(customer);
        repository.delete(savedCustomer);
        Customer deletedCustomer = repository.findOne(savedCustomer.getId());
        Assertions.assertThat(deletedCustomer).isNotNull();
        Assertions.assertThat(deletedCustomer.getId()).isNotNull();
        Assertions.assertThat(deletedCustomer.getDateCreate()).isNotNull();
        Assertions.assertThat(deletedCustomer.getDateDelete()).isNotNull();
        Assertions.assertThat(deletedCustomer.isDeleted()).isTrue();
    }

    @Test
    public void testRepositoryDeleteByIdUpdateDeletedToTrueAndDateDeleteToNewDate() {
        Customer savedCustomer = repository.save(customer);
        repository.delete(savedCustomer.getId());
        Customer deletedCustomer = repository.findOne(savedCustomer.getId());
        Assertions.assertThat(deletedCustomer).isNotNull();
        Assertions.assertThat(deletedCustomer.getId()).isNotNull();
        Assertions.assertThat(deletedCustomer.getDateCreate()).isNotNull();
        Assertions.assertThat(deletedCustomer.getDateDelete()).isNotNull();
        Assertions.assertThat(deletedCustomer.isDeleted()).isTrue();
    }

    @Test
    public void testAllRepositoryMethods() {

        printCustomers();

        // create
        Customer createdCustomer = repository.save(customer);

        printCustomers();

        assertThat("Created customer should not be null value", createdCustomer, notNullValue());
        assertThat("Created customer id should not be null value", createdCustomer.getId(), notNullValue());
        assertThat("Created customer create date should not be null value", createdCustomer.getDateCreate(), notNullValue());
        assertThat("Created customer update date should be null value", createdCustomer.getDateUpdate(), nullValue());
        assertThat("Created customer delete date should be null value", createdCustomer.getDateDelete(), nullValue());
        assertThat("Created customer flag deleted should be false", createdCustomer.isDeleted(), is(false));

        // retrieve
        Customer retrievedCustomer = repository.findOne(createdCustomer.getId());

        printCustomers();

        assertThat("Retrieved customer should not be null value", createdCustomer, notNullValue());
        assertThat("Retrieved customer id should not be null value", createdCustomer.getId(), notNullValue());
        assertThat("Retrieved customer create date should not be null value", createdCustomer.getDateCreate(), notNullValue());
        assertThat("Retrieved customer update date should be null value", createdCustomer.getDateUpdate(), nullValue());
        assertThat("Retrieved customer delete date should be null value", createdCustomer.getDateDelete(), nullValue());
        assertThat("Retrieved customer flag deleted should be false", createdCustomer.isDeleted(), is(false));

        // update
        retrievedCustomer.setLastname("smith");
        Customer updatedCustomer = repository.save(retrievedCustomer);

        printCustomers();

        assertThat("Updated customer should not be null value", updatedCustomer, notNullValue());
        assertThat("Updated customer last name should be 'smith'", updatedCustomer.getLastname(), Matchers.is("smith"));
        assertThat("Updated customer id should not be null value", updatedCustomer.getId(), notNullValue());
        assertThat("Updated customer create date should not be null value", updatedCustomer.getDateCreate(), notNullValue());
        // assertThat("Updated customer update date should be null value", updatedCustomer.getDateUpdate(), nullValue());
        assertThat("Updated customer delete date should be null value", updatedCustomer.getDateDelete(), nullValue());
        assertThat("Updated customer flag deleted should be false", updatedCustomer.isDeleted(), is(false));

        // delete
    }

    private void printCustomers() {
        Iterable<Customer> customers = repository.findAll();
        customers.forEach(System.out::println);
    }
}