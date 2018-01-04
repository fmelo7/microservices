package com.example.app.webclient.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CustomerSource {
    String ADD_CUSTOMER = "addCustomer";

    @Output("addCustomer")
    MessageChannel addCustomer();
}
