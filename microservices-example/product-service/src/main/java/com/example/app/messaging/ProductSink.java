package com.example.app.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ProductSink {
    String ADD_PRODUCT = "addProduct";

    @Input("addProduct")
    SubscribableChannel input();
}
