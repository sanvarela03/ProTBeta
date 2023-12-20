package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.Address;
import com.example.springbootjwtauthentication.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repository;

    public Address saveAddress(Address address) {
        return repository.save(address);
    }

    public Address getAddressById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("No se pudo encontrar la dirección con ID: " + id));
    }
}
