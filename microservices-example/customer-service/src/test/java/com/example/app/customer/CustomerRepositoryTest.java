package com.example.app.customer;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

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
        Customer updatedCustomer = repository.save(savedCustomer);
        Assertions.assertThat(updatedCustomer).isNotNull();
        Assertions.assertThat(updatedCustomer.getId()).isEqualTo(savedCustomer.getId());
        Assertions.assertThat(updatedCustomer.getDateUpdate()).isNotNull();
        Assertions.assertThat(updatedCustomer.getDateDelete()).isNull();
        Assertions.assertThat(updatedCustomer.isDeleted()).isFalse();
    }

    @Test
    public void testRepositoryCanUpdateLastnameCustomer() throws Exception {
        Customer updatedCustomer = repository.save(customer);
        updatedCustomer.setLastname("smith");
        repository.save(updatedCustomer);
    }

    @Test
    public void testRepositoryDeleteEntityUpdateDeletedToTrueAndDateDeleteToNewDate() {
        Customer savedCustomer = repository.save(customer);
        repository.delete(savedCustomer);
        Customer deletedCustomer = repository.findOne(savedCustomer.getId());
        Assertions.assertThat(deletedCustomer).isNotNull();
        Assertions.assertThat(deletedCustomer.getId()).isNotNull();
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
        Assertions.assertThat(deletedCustomer.getDateDelete()).isNotNull();
        Assertions.assertThat(deletedCustomer.isDeleted()).isTrue();
    }
}