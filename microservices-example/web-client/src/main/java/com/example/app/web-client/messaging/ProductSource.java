package com.example.app.customer.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ProductSource {
    String ADD_PRODUCT = "addProduct";

    @Output("addProduct")
    MessageChannel addProduct();
}
