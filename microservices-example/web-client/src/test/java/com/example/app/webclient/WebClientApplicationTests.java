package com.example.app.webclient;

import com.example.app.webclient.service.CustomerWebService;
import com.example.app.webclient.service.ProductWebService;
import com.example.app.webclient.vo.Customer;
import com.example.app.webclient.vo.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class WebClientApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    private CustomerWebService customerWebService;

    @Mock
    private ProductWebService productWebService;

    @Test
    public void contextLoads() {
        given(customerWebService.getAll()).willReturn(Collections.emptyList());
        assertThat(customerWebService.getAll()).isNotNull();

        given(productWebService.getAll()).willReturn(Collections.emptyList());
        assertThat(productWebService.getAll()).isNotNull();
    }

    @Test
    public void integrationCustomerWebServiceTest() {
        // arrange

        // act
        ResponseEntity<Customer[]> response = restTemplate.getForEntity(
                "/api/v1/customers",
                Customer[].class);

        // assert
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void integrationProductWebServiceTest() {
        // arrange

        // act
        ResponseEntity<Product[]> response = restTemplate.getForEntity(
                "/api/v1/products",
                Product[].class);

        // assert
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
    }

}
