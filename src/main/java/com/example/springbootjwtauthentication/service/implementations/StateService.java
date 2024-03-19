package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.State;
import com.example.springbootjwtauthentication.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateService {
    @Autowired
    private StateRepository repository;

    public void save(State state) {
        repository.save(state);
    }

    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    public State getFiltered(String country, String state) {
        return repository.findFiltered(country, state).orElseThrow(() -> new RuntimeException("No se encontro: " + country + ", " + state + "; "));
    }
}
