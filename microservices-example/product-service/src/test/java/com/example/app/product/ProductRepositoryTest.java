package com.example.app.product;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    private Product product;

    @Before
    public void setUp() throws Exception {
        product = new Product("Spring Booot", 1.99);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testRepositoryCannotCreateWithNullValues() {
        repository.save(new Product());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testRepositoryCannotCreateWithNameNull() {
        product.setName(null);
        repository.save(product);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testRepositoryCannotCreateWithPriceNull() {
        product.setPrice(null);
        repository.save(product);
    }

    @Test
    public void testRepositoryCanCreateWithValues() {
        Product savedProduct = repository.save(product);
        Assertions.assertThat(savedProduct).isNotNull();
        Assertions.assertThat(savedProduct.getDateCreate()).isNotNull();
        Assertions.assertThat(savedProduct.getDateUpdate()).isNull();
        Assertions.assertThat(savedProduct.getDateDelete()).isNull();
        Assertions.assertThat(savedProduct.isDeleted()).isFalse();
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testRepositoryCannotUpdateWithNullValues() {
        Product updatedProduct = repository.save(product);
        updatedProduct.setName(null);
        updatedProduct.setPrice(null);
        repository.save(updatedProduct);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testRepositoryCannotUpdateWithNameNull() {
        Product updatedProduct = repository.save(product);
        updatedProduct.setName(null);
        repository.save(updatedProduct);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testRepositoryCannotUpdateWithPriceNull() {
        Product updatedProduct = repository.save(product);
        updatedProduct.setPrice(null);
        repository.save(updatedProduct);
    }

    @Test
    public void testRepositoryCanUpdateWithValues() {
        Product savedProduct = repository.save(this.product);
        savedProduct.setName("Spring Cloud");
        Product updatedProduct = repository.save(this.product);
        updatedProduct.setPrice(BigDecimal.valueOf(2.99));
        Assertions.assertThat(updatedProduct).isNotNull();
        Assertions.assertThat(updatedProduct.getDateCreate()).isNotNull();
        Assertions.assertThat(updatedProduct.getDateUpdate()).isNotNull();
        Assertions.assertThat(updatedProduct.getDateDelete()).isNull();
        Assertions.assertThat(updatedProduct.isDeleted()).isFalse();
    }

    @Test
    public void testRepositoryCanDeleteByEntityThenUpdateDeletedToTrueAndDateDeleteToNewDate() {
        Product savedProduct = repository.save(product);
        repository.delete(savedProduct);
        Product deletedProduct = repository.findOne(savedProduct.getId());
        Assertions.assertThat(deletedProduct).isNotNull();
        Assertions.assertThat(deletedProduct.getDateCreate()).isNotNull();
        Assertions.assertThat(deletedProduct.getDateDelete()).isNotNull();
        Assertions.assertThat(deletedProduct.isDeleted()).isTrue();
    }

    @Test
    public void testRepositoryCanDeleteByIdThenUpdateDeletedToTrueAndDateDeleteToNewDate() {
        Product savedProduct = repository.save(product);
        repository.delete(savedProduct.getId());
        Product deletedProduct = repository.findOne(savedProduct.getId());
        Assertions.assertThat(deletedProduct).isNotNull();
        // Assertions.assertThat(deletedProduct.getDateCreate()).isNotNull();
        Assertions.assertThat(deletedProduct.getDateDelete()).isNotNull();
        Assertions.assertThat(deletedProduct.isDeleted()).isTrue();
    }

}