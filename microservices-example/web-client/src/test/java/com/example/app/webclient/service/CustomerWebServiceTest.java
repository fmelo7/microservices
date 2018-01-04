package com.example.app.webclient.service;

import com.example.app.webclient.messaging.CustomerSource;
import com.example.app.webclient.vo.Customer;
import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.hal.ResourcesMixin;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.example.app.webclient.service.CustomerWebService.SERVICE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpMethod.GET;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class CustomerWebServiceTest {

    @Autowired
    private CustomerWebService service;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private CustomerSource source;

    private ParameterizedTypeReference<Resources<Customer>> typeReference = new ParameterizedTypeReference<Resources<Customer>>() {
    };

    @Test
    public void getAllFallbackAndReturnAnEmptyList() {
        given(restTemplate.exchange(SERVICE_URL, GET, null, typeReference)).willThrow(new IllegalStateException());

        assertThat(service.getAll()).isEmpty();
    }

    @Test
    public void getAllAndReturnAnListOfCustomers() {
        restTemplate = mock(RestTemplate.class);
        source = mock(CustomerSource.class);
        service = new CustomerWebService(restTemplate, source);

        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer("john", "doe");
        customers.add(customer);

        ResponseEntity responseEntity = mock(ResponseEntity.class);
        Resources<Customer> resources = new ResourcesMixin<Customer>() {
            @Override
            public Collection<Customer> getContent() {
                return customers;
            }
        };
        given(responseEntity.getBody()).willReturn(resources);
        given(restTemplate.exchange(SERVICE_URL, GET, null, typeReference)).willReturn(responseEntity);

        assertThat(service.getAll()).contains(customer);
    }

    @Test
    public void addCustomerAndReturnTrue() {
        restTemplate = mock(RestTemplate.class);
        source = mock(CustomerSource.class);
        service = new CustomerWebService(restTemplate, source);

        Customer customer = new Customer("john", "doe");

        MessageChannel messageChannel = mock(MessageChannel.class);
        given(source.addCustomer()).willReturn(messageChannel);
        given(messageChannel.send(MessageBuilder.withPayload(anyString()).build())).willReturn(true);

        assertThat(service.addCustomer(customer)).isTrue();
    }

    @Configuration
    @EnableAspectJAutoProxy
    public static class SpringConfig {

        @Bean
        public HystrixCommandAspect hystrixCommandAspect() {
            return new HystrixCommandAspect();
        }

        @Bean
        public CustomerWebService serviceFallback() {
            return new CustomerWebService();
        }
    }
}