package com.example.app.webclient.controller;

import com.example.app.webclient.service.CustomerWebService;
import com.example.app.webclient.vo.Customer;
import org.junit.After;
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
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerWebService service;

    private Customer customer = new Customer();

    private List<Customer> customers = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        customer = new Customer("john", "doe");
        customers.add(customer);
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
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].firstname").value("john"))
                .andExpect(jsonPath("$[0].lastname").value("doe"));
    }

    @Test
    public void addCustomerAndReturnTrue() throws Exception {
        given(service.addCustomer(any())).willReturn(true);
        mockMvc.perform(post("/api/v1/customers")
                .content(json(customer))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("index-static"));
    }

    @Test(expected = NestedServletException.class)
    public void addCustomerAndReturnNestedServletException() throws Exception {
        given(service.addCustomer(any())).willThrow(new RuntimeException());
        mockMvc.perform(post("/api/v1/customers").content(json(customer))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is5xxServerError());
    }

    @Test(expected = NestedServletException.class)
    public void getAllFallback() throws Exception {
        given(service.getAll()).willThrow(new RuntimeException());
        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().is4xxClientError());
    }
}