package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.Country;
import com.example.springbootjwtauthentication.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService {
    @Autowired
    private CountryRepository repository;

    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    public Country getByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new RuntimeException("No se econtro: " + name));
    }

    public Country save(Country country) {
        return repository.save(country);
    }
}
