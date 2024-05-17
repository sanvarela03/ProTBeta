package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.Address;
import com.example.springbootjwtauthentication.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<List<Vehicle>> findAllByTransporter_Id(Long id);
}
