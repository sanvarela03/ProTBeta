package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.Transporter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransporterRepository extends JpaRepository<Transporter, Long> {
    Optional<Transporter> findByUsername(String username);

    @Query(
            value = "SELECT t FROM Transporter t WHERE t.currentAddress.city.id = ?1"
    )
    Optional<List<Transporter>> findAllByCityId(Long id);

    @Query("SELECT t FROM Transporter t WHERE t.isAvailable = true")
    Optional<List<Transporter>> findAllAvailable();

    @Query(
            value = "SELECT AVG(t.currentVehicle.fuelConsumption) FROM Transporter t WHERE t.currentAddress.city.id = ?1 AND t.isAvailable = true"
    )
    Optional<Double> averageFuelConsumptionByCityId(Long id);

    @Query(
            value = "SELECT t.currentVehicle.fuelConsumption FROM Transporter t WHERE t.currentAddress.city.id = ?1 AND t.isAvailable = true"
    )
    Optional<List<Double>> findAllAvailableFuelConsumptionByCityId(Long id);
}
