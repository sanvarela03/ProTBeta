package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.Producer;
import com.example.springbootjwtauthentication.model.Transporter;
import com.example.springbootjwtauthentication.repository.ProducerRepository;
import com.example.springbootjwtauthentication.repository.TransporterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransporterService {

    @Autowired
    private TransporterRepository repository;

    public Transporter getTransporterByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró el transportador con usuario: " + username));
    }

    public List<Transporter> getAllTransporters() {
        return repository.findAll();
    }

    public Transporter saveTransporter(Transporter transporter) {
        return repository.save(transporter);
    }
}
