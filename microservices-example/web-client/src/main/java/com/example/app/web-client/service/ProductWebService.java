package com.example.app.customer.service;

import com.example.app.customer.vo.Product;
import com.example.app.customer.messaging.ProductSource;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableBinding(ProductSource.class)
public class ProductWebService {

    private static String SERVICE_URL = "http://PRODUCT-SERVICE/products";

    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    @Autowired
    private ProductSource source;

    public List<Product> getAllFallback() {
        return Collections.emptyList();
    }

    @HystrixCommand(fallbackMethod = "getAllFallback")
    public List<Product> getAll() {
        return restTemplate
                .exchange(
                        SERVICE_URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Resources<Product>>() {
                        })
                .getBody()
                .getContent()
                .stream()
                .collect(Collectors.toList());
    }

    @HystrixCommand
    public boolean addProduct(Product product) {
        return source.addProduct().send(MessageBuilder.withPayload(product.toString()).build());
    }
}
