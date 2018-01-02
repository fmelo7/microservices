package com.example.app.messaging;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.MessageEndpoint;

@MessageEndpoint
@EnableBinding(ProductSink.class)
public class ProductProcessor {

    @StreamListener(ProductSink.ADD_PRODUCT)
    public void addProduct(String product) {
        System.out.println("product: " + product);
        // TODO repository save product
    }
}
