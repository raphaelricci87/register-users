package com.registration.users.service.impl;

import java.util.List;
import com.registration.users.model.Customer;
import com.registration.users.repository.CustomerRepository;
import com.registration.users.service.RegisterService;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RegisterServiceImpl implements RegisterService{

    CustomerRepository register;
    
    public RegisterServiceImpl(CustomerRepository register) {
        this.register = register;
    }
    
    @Override
    public Customer findById(Long id){
        return register.findById(id)
        .orElseThrow( () -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        });    
    }

    @Override
    public Customer save(Customer customer){
        return register.save(customer);
    }

    @Override
    public void delete(Long id){
        register.findById(id)
            .map( customer -> {  register.delete(customer);
            return customer;
        }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                                    "Customer not found") );
    }

    @Override
    public void update(Long id, Customer customer){
        register.findById(id)
            .map( updatedCustomer -> {  register.delete(customer);
            customer.setId(updatedCustomer.getId());
            return updatedCustomer;
        }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                                    "Customer not found") );
    }

    @Override
    public List<Customer> find(Customer filter){

        ExampleMatcher matcher = 
        ExampleMatcher.matching()
                       .withIgnoreCase()
                       .withStringMatcher(StringMatcher.CONTAINING);
        
        Example example = Example.of(filter, matcher);
        return register.findAll(example);
    }
}