package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.Address;
import com.example.springbootjwtauthentication.repository.AddressRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Address> getAllAddressByUserId(Long userId) {
        return repository.findAllByUser_Id(userId).orElseThrow(() -> new RuntimeException("No se pudo encontrar las direcciones asociadas con el usuario con id: " + userId));
    }

    public void deleteById(Long addressId) {
        repository.removeById(addressId);
    }
}
