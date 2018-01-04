package com.example.app.webclient.controller;

import com.example.app.webclient.service.ProductWebService;
import com.example.app.webclient.vo.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductWebService service;

    private Product product;

    private List<Product> products;

    @Before
    public void setup() {
        products = new ArrayList<>();
        product = new Product("Spring Boot", 0.63);
        products.add(product);
    }

    @Test
    public void getAll() throws Exception {
        given(service.getAll()).willReturn(products);
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].name").value("Spring Boot"))
                .andExpect(jsonPath("$.[0].price").value(0.63));
    }

    @Test
    public void addProductAndReturnTrue() throws Exception {
        given(service.addProduct(any())).willReturn(true);
        mockMvc.perform(post("/api/v1/products").content(json(product))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("index-static"));
    }

    @Test(expected = NestedServletException.class)
    public void addProductAndReturnNestedServletException() throws Exception {
        given(service.addProduct(any())).willThrow(new RuntimeException());
        mockMvc.perform(post("/api/v1/products").content(json(product))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is5xxServerError());
    }

    @Test(expected = NestedServletException.class)
    public void getAllFallback() throws Exception {
        given(service.getAll()).willThrow(new RuntimeException());
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().is5xxServerError());
    }

}