package com.registration.users.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.registration.users.model.Customer;
import com.registration.users.model.CustomerAddress;
import com.registration.users.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import junit.framework.Assert;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CustomerControllerTests {

    final String BASE_PATH = "http://localhost:8081/customer-register/";
    
    @Autowired
    private CustomerRepository repository;

    private RestTemplate restTemplate;

    private ObjectMapper MAPPER = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        repository.deleteAll();
        restTemplate = new RestTemplate();
    }

    @Test
    public void testCreateCustomer() throws JsonProcessingException{
 
        CustomerAddress address = new CustomerAddress();
        address.setStreetAddress("rua a");
        address.setNumber(1L);
        address.setPostalCode("06401000");
        address.setCity("São Paulo");
        address.setCountry("BR");
        address.setAddressComplement("BL 12");
        Customer customer = new Customer(null,"raphael","04059667099",address);
        
        repository.save(customer); 
        Customer response = restTemplate.postForObject(BASE_PATH, customer, Customer.class);
 
        //Verificamos se o resultado da requisição é igual ao esperado
        //Se sim significa que tudo correu bem
        Assert.assertEquals("raphael", response.getName());
    }

}