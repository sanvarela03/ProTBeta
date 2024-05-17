package com.example.springbootjwtauthentication.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateVehicleRequest {
    private String brand;
    private String model;
    private String year;
    private String fuelType;
    private double fuelConsumption;
    private String fuelConsumptionUnit;
    private double cargoVolume;
    private double payload;
    private Boolean isCurrentVehicle;
}



