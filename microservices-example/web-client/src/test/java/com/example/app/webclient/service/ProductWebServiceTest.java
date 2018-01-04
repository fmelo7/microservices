package com.example.app.webclient.service;

import com.example.app.webclient.messaging.ProductSource;
import com.example.app.webclient.vo.Product;
import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import org.junit.Before;
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

import static com.example.app.webclient.service.ProductWebService.SERVICE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpMethod.GET;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class ProductWebServiceTest {

    @Autowired
    private ProductWebService serviceFallback;

    @MockBean
    private RestTemplate restTemplate;

    private ProductSource source;

    private ProductWebService service;

    private Product product;

    private List<Product> products;

    private ParameterizedTypeReference<Resources<Product>> typeReference;

    @Before
    public void setUp() throws Exception {
        product = new Product("Boo", 1.0);
        products = new ArrayList<>();
        products.add(product);

        typeReference = new ParameterizedTypeReference<Resources<Product>>() {
        };
    }

    @Test
    public void getAllFallbackAndReturnEmptyList() throws Exception {
        given(restTemplate.exchange(SERVICE_URL, GET, null, typeReference)).willThrow(new IllegalStateException());

        assertThat(serviceFallback.getAll()).isEmpty();
    }

    @Test
    public void getAllAndReturnAListOfProducts() throws Exception {
        restTemplate = mock(RestTemplate.class);
        source = mock(ProductSource.class);
        service = new ProductWebService(restTemplate, source);
        ResponseEntity responseEntity = mock(ResponseEntity.class);
        Resources<Product> resources = new ResourcesMixin<Product>() {
            @Override
            public Collection<Product> getContent() {
                return products;
            }
        };
        given(responseEntity.getBody()).willReturn(resources);
        given(restTemplate.exchange(SERVICE_URL, GET, null, typeReference)).willReturn(responseEntity);

        assertThat(service.getAll()).contains(product);
    }

    @Test
    public void addProductAndReturnTrue() throws Exception {
        restTemplate = mock(RestTemplate.class);
        source = mock(ProductSource.class);
        service = new ProductWebService(restTemplate, source);
        MessageChannel messageChannel = mock(MessageChannel.class);
        given(source.addProduct()).willReturn(messageChannel);
        given(messageChannel.send(MessageBuilder.withPayload(anyString()).build())).willReturn(true);

        assertThat(service.addProduct(product)).isTrue();
    }

    @Configuration
    @EnableAspectJAutoProxy
    public static class SpringConfig {

        @Bean
        public HystrixCommandAspect hystrixCommandAspect() {
            return new HystrixCommandAspect();
        }

        @Bean
        public ProductWebService serviceFallback() {
            return new ProductWebService();
        }
    }
}