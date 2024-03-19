package com.example.springbootjwtauthentication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(
        name = "vehicles"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle implements Serializable {
    @Id
    @Column(name = "vehicle_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private String year;
    private String vin;
    private String fuelType;
    private double fuelConsumption;
    private String fuelConsumptionUnit; // L/100Km
    private double cargoVolume;
    private double payload;
    @ManyToOne
    @JoinColumn(name = "transporter_id")
    private Transporter owner;

    @Serial
    private static final long serialVersionUID = 1L;
}
