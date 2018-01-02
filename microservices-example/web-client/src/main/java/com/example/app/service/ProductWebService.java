package com.example.app.service;

import com.example.app.messaging.ProductSource;
import com.example.app.vo.Product;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@EnableBinding(ProductSource.class)
public class ProductWebService {

    private static String SERVICE_URL = "http://PRODUCT-SERVICE/api/v1/products";

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
        return Arrays.asList(restTemplate.getForObject(SERVICE_URL, Product[].class));
    }

    public void addProduct(Product product) {
        source.addProduct().send(MessageBuilder.withPayload(product.toString()).build());
    }
}
