package com.example.app.customer.controller;

import com.example.app.customer.service.CustomerWebService;
import com.example.app.customer.vo.Customer;
import org.junit.After;
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
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerWebService service;

    List<Customer> customers = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        customers.add(new Customer("john", "doe"));
    }

    @After
    public void tearDown() throws Exception {
        customers = new ArrayList<>();
    }

    @Test
    public void getAllShouldReturnAListOfCustomers() throws Exception {
        given(service.getAll()).willReturn(customers);
        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("customers").isArray())
                .andExpect(jsonPath("firstname").value("john"))
                .andExpect(jsonPath("lastname").value("doe"));
    }

    @Test
    public void addCustomer() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void getAllFallback() throws Exception {
        given(service.getAll()).willThrow(new RuntimeException());
        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().is4xxClientError());
    }
}