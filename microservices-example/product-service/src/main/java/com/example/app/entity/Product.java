package com.example.app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private BigDecimal price;

    public Product() {
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = BigDecimal.valueOf(price);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
