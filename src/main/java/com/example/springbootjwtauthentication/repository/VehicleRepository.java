package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {


}
