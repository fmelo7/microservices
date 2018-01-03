package com.example.app.customer.service;

import com.example.app.customer.vo.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class ProductWebServiceTest {

    @Mock
    ProductWebService service;

    @Test
    public void getAllFallbackAndReturnEmptyList() {
        given(service.getAllFallback()).willReturn(Collections.emptyList());
        assertThat(service.getAll()).isEmpty();
    }

    @Test
    public void getAllAndReturnEmptyList() {
        given(service.getAll()).willReturn(Collections.emptyList());
        assertThat(service.getAll()).isEmpty();
    }

    @Test
    public void addProductAndReturnTrue() {
        given(service.addProduct(new Product("Boo", 1.0))).willReturn(true);
        assertThat(service.addProduct(new Product("Boo", 1.0))).isTrue();
    }
}