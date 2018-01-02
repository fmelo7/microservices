package com.example.app.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CustomerSink {
    String ADD_CUSTOMER = "addCustomer";

    @Input("addCustomer")
    SubscribableChannel addCustomer();
}
