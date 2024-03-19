package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.Enum.ERate;
import com.example.springbootjwtauthentication.model.Rate;
import com.example.springbootjwtauthentication.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateService {

    @Autowired
    private RateRepository repository;

    public Rate getRateByName(ERate name) {
        return repository.findByName(name).orElseThrow(() -> new RuntimeException("No se encontró el nombre de la tarifa"));
    }

}
