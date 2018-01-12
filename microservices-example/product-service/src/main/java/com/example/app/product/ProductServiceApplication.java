package com.example.app.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.SubscribableChannel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@SpringBootApplication
@EnableEurekaClient
@EnableIntegration
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Autowired
    private ProductRepository repository;

    @Bean
    CommandLineRunner runner() {
        return args -> {
            repository.save(new Product("Spring Boot", Math.random() * 1.5));
            repository.save(new Product("Spring Cloud", Math.random() * 1.5));
            repository.save(new Product("Eureka", Math.random() * 1.4));
            repository.save(new Product("Html", Math.random() * 5.0));
            repository.save(new Product("jQuery", Math.random() * 3.21));
            repository.save(new Product("Bootstrap", Math.random() * 3.32));
        };
    }
}

interface ProductSink {
    String ADD_PRODUCT = "addProduct";

    @Input("addProduct")
    SubscribableChannel input();
}

@MessageEndpoint
@EnableBinding(ProductSink.class)
class ProductProcessor {

    @Autowired
    private ProductRepository repository;

    @ServiceActivator(inputChannel = ProductSink.ADD_PRODUCT)
    public Product addProduct(String product) {
        // TODO eval string json to a map or something else...
        return repository.save(new Product(product, 0.0));
    }
}


@RepositoryRestResource
interface ProductRepository extends CrudRepository<Product, UUID> {
    @Override
    default void delete(UUID uuid) {
        Product product = findOne(uuid);
        delete(product);
    }

    @Override
    default void delete(Product entity) {
        entity.setDeleted(true);
        save(entity);
    }
}

@Entity
class Product {

    @Id
    @GeneratedValue
    private UUID _id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(insertable = false)
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDelete;

    @Column(nullable = false)
    private Boolean deleted = false;

    public Product() {
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = BigDecimal.valueOf(price);
    }

    public UUID getId() {
        return _id;
    }

    public void setId(UUID id) {
        this._id = id;
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
