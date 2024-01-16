package com.example.springbootjwtauthentication.service.implementations;


import com.example.springbootjwtauthentication.model.Enum.EStatus;
import com.example.springbootjwtauthentication.model.Status;
import com.example.springbootjwtauthentication.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    @Autowired
    private StatusRepository repository;

    public Status getStatusByName(EStatus name) {
        return repository.findByName(name)
                .orElseThrow(() -> new RuntimeException("No se encontró el estado con nombre : " + name));
    }
}
