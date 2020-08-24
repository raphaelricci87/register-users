package com.registration.users.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.registration.users.model.Customer;
import com.registration.users.repository.CustomerRepository;
import com.registration.users.service.RegisterService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    RegisterService service;

    public CustomerController(RegisterService service) {
        this.service = service;
    }
    
    @GetMapping(value = "{id}")
    @ResponseBody
    public Customer getCustomerById(@PathVariable Long id){
        return service.findById(id);
    } 

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer save (@RequestBody @Valid Customer customer){
        return service.save(customer);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete (@PathVariable Long id){
        service.delete(id);
    }

    @PutMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update (@PathVariable Long id, 
                        @RequestBody @Valid Customer customer){
        service.update(id, customer);
    }

    @GetMapping
    public List<Customer> find (Customer filter){
        return service.find(filter);
    }
}