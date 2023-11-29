package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.Customer;
import com.example.springbootjwtauthentication.model.Producer;
import com.example.springbootjwtauthentication.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repository;

    public List<Customer> getAllProducers() {
        return repository.findAll();
    }

    public Customer getCustomerByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("No se encontró el cliente con usuario: " + username));
    }

    public Customer saveCustomer(Customer customer) {
        return repository.save(customer);
    }

    public void deleteCustomer(Long customerId) {
        repository.deleteById(customerId);
    }
}
