package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.Transporter;
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

    public Transporter getById(Long userId) {
        return repository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User not found with ID: " + userId));
    }


    public List<Transporter> getAllTransporters() {
        return repository.findAll();
    }

    public List<Transporter> getAllTransportersByCityId(Long id) {
        return repository.findAllByCityId(id)
                .orElseThrow(() -> new RuntimeException("No se encontro la ciudad con id: " + id));
    }

    public List<Double> getAllFuelConsumptionByCityId(Long id) {
        return repository.findAllAvailableFuelConsumptionByCityId(id).orElseThrow(() -> new RuntimeException("No fuel consumption found in city id: " + id));
    }

    public Double getAverageFuelConsumptionByCityId(Long id) {
        return repository.averageFuelConsumptionByCityId(id).orElseThrow(() -> new RuntimeException("No city found with id: " + id));
    }

    public Transporter getByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new RuntimeException("No transporter found with username: " + username));
    }

    public Transporter saveTransporter(Transporter transporter) {
        return repository.save(transporter);
    }
}
