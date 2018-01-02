package com.example.app.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ProductSource {
    String ADD_PRODUCT = "addProduct";

    @Output("addProduct")
    MessageChannel addProduct();
}
