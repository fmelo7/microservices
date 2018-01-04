package com.example.app.webclient.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ProductSource {
    String ADD_PRODUCT = "addProduct";

    @Output("addProduct")
    MessageChannel addProduct();
}
