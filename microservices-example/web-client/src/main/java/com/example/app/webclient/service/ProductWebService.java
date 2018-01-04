package com.example.app.webclient.service;

import com.example.app.webclient.messaging.ProductSource;
import com.example.app.webclient.vo.Product;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpMethod.GET;

@Service
@EnableBinding(ProductSource.class)
public class ProductWebService {

    public static String SERVICE_URL = "http://PRODUCT-SERVICE/products";

    protected RestTemplate restTemplate;

    private ProductSource source;

    public ProductWebService() {
    }

    @Autowired
    public ProductWebService(RestTemplate restTemplate, ProductSource source) {
        this.restTemplate = restTemplate;
        this.source = source;
    }

    public List<Product> getAllFallback() {
        return Collections.emptyList();
    }

    @HystrixCommand(fallbackMethod = "getAllFallback")
    public List<Product> getAll() {
        ResponseEntity<Resources<Product>> responseEntity = restTemplate.exchange(
                SERVICE_URL,
                GET,
                null,
                new ParameterizedTypeReference<Resources<Product>>() {
                });
        return responseEntity.getBody()
                .getContent()
                .stream()
                .collect(Collectors.toList());
    }

    @HystrixCommand
    public boolean addProduct(Product product) {
        return source.addProduct().send(MessageBuilder.withPayload(product.toString()).build());
    }
}
