package com.example.app.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.SubscribableChannel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
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
interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}

@Entity
class Product {

    @Id
    @GeneratedValue
    private UUID _id;
    private String name;
    private BigDecimal price;

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

}
