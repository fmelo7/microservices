package com.example.app.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    // product table construction
    private Product product;

    // some date to test
    private Date someDate;

    @Before
    public void setUp() throws Exception {
        product = new Product("Spring Boot", 1.99);
        someDate = new Date();
    }

    @Test
    public void testProductFieldIdUUID() {
        UUID uuid = UUID.randomUUID();
        product.setId(uuid);
        assertThat(product.getId()).isEqualTo(uuid);
    }

    @Test
    public void testProductName() {
        // name String 150
        String name = "some name";
        product.setName(name);
        assertThat(product.getName()).isEqualTo(name);
    }

    @Test
    public void testProductPrice() {
        // price bigdecimal
        BigDecimal price = BigDecimal.valueOf(1.99);
        product.setPrice(price);
        assertThat(product.getPrice()).isEqualTo(price);
    }

    @Test
    public void ProductDeleted() {
        // deleted boolean flag of register deleted
        boolean someBoolean = Math.random() < 0.5;
        product.setDeleted(someBoolean);
        assertThat(product.isDeleted()).isEqualTo(someBoolean);
    }

    @Test
    public void ProductIsDeletedThenDateDeleteMustHaveValue() {
        // deleted boolean flag of register deleted
        boolean someBoolean = true;
        product.setDeleted(someBoolean);
        assertThat(product.isDeleted()).isEqualTo(someBoolean);
        assertThat(product.getDateDelete()).isEqualTo(someDate);
    }

    @Test
    public void ProductIsNotDeletedThenDateDeleteMustNotHaveValue() {
        // deleted boolean flag of register deleted
        boolean someBoolean = false;
        product.setDeleted(someBoolean);
        assertThat(product.isDeleted()).isEqualTo(someBoolean);
        assertThat(product.getDateDelete()).isEqualTo(null);
    }

}