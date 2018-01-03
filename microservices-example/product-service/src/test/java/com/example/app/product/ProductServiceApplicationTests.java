package com.example.app.product;

import com.example.app.product.Product;
import com.example.app.product.ProductProcessor;
import com.example.app.product.ProductSink;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceApplicationTests {

    @Autowired
    private ProductProcessor productProcessor;

    @Autowired
    private ProductSink productSink;

    @Test
    public void contextLoads() {
        assertThat(new Product()).isNotNull();
        assertThat(productSink.input()).isNotNull();
        assertThat(productProcessor.addProduct(anyString())).isNotNull();
    }

}
