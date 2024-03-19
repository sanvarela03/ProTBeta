package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.Vehicle;
import com.example.springbootjwtauthentication.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository repository;

    public Vehicle addVehicle(Vehicle vehicle) {
        return repository.save(vehicle);
    }
}
