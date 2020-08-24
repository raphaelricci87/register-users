package com.registration.users.service;

import java.util.List;
import java.util.Optional;

import com.registration.users.model.Customer;

public interface RegisterService {
    
    public Customer findById(Long id);
    public Customer save(Customer customer);
    public void delete(Long id);
    public void update(Long id, Customer customer);
    public List<Customer> find(Customer filter);
}