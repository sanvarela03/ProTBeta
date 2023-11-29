package com.example.springbootjwtauthentication.service.implementations;


import com.example.springbootjwtauthentication.model.Enum.EState;
import com.example.springbootjwtauthentication.model.State;
import com.example.springbootjwtauthentication.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateService {

    @Autowired
    private StateRepository repository;

    public State getSateByName(EState name) {
        return repository.findByName(name)
                .orElseThrow(() -> new RuntimeException("No se encontró el estado con nombre : " + name));
    }
}
