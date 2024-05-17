package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.Vehicle;
import com.example.springbootjwtauthentication.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository repository;

    public Vehicle addVehicle(Vehicle vehicle) {
        return repository.save(vehicle);
    }

    public void delete(Long vehicleId) {
        repository.deleteById(vehicleId);
    }

    public void save(Vehicle vehicle) {
        repository.save(vehicle);
    }

    public Vehicle getVehicleById(Long vehicleId) {
        return repository.findById(vehicleId).orElseThrow(() -> new RuntimeException("No se encontró vehiculo con id : " + vehicleId));
    }


    public List<Vehicle> getAllVehiclesByTransporterId(Long id) {
        return repository.findAllByTransporter_Id(id)
                .orElseThrow(() -> new RuntimeException("No se pudo encontrar los vehiculos asociados con el transportador con id: " + id));
    }
}
