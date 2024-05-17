package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.Enum.ERole;
import com.example.springbootjwtauthentication.model.Producer;
import com.example.springbootjwtauthentication.model.Role;
import com.example.springbootjwtauthentication.repository.ProducerRepository;
import com.example.springbootjwtauthentication.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProducerService {
    @Autowired
    private ProducerRepository repository;

    public List<Producer> getAllProducers() {
        return repository.findAll();
    }

    public List<Producer> getProducersByCity(String city) {

        return repository.findAllByCity(city).orElseThrow(() -> new RuntimeException("No se encontró productures en Producer Service con ciudad : " + city));
    }

    public Producer getProducerById(Long producerId) {
        return repository.findById(producerId)
                .orElseThrow(() -> new RuntimeException("No se encontró el productor con ID: " + producerId));
    }

    public Producer getProducerByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró el productor con usuario: " + username));
    }

    public Producer saveProducer(Producer producer) {
        return repository.save(producer);
    }

    public void deleteProducer(Long producerId) {
        repository.deleteById(producerId);
    }
}
