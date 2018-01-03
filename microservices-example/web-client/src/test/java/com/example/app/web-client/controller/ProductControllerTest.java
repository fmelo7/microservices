package com.example.app.customer.controller;

import com.example.app.customer.service.ProductWebService;
import com.example.app.customer.vo.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductWebService service;

    private List<Product> products;

    @Before
    public void setup() {
        products = new ArrayList<>();
        products.add(new Product("Spring Boot", 0.63));
    }

    @Test
    public void getAll() throws Exception {
        given(service.getAll()).willReturn(products);
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Spring Boot"))
                .andExpect(jsonPath("price").value(0.63));
    }

    @Test
    public void getAllFallback() throws Exception {
        given(service.getAll()).willThrow(new RuntimeException());
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().is5xxServerError());
    }
}