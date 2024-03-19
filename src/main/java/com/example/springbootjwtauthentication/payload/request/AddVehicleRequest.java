package com.example.springbootjwtauthentication.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddVehicleRequest {
    @NotBlank
    private String brand;
    @NotBlank
    private String model;
    @NotBlank
    private String year;
    @NotBlank
    private String vin;
    @NotBlank
    private String fuelType;
    private double fuelConsumption;
    private String fuelConsumptionUnit;
    private double cargoVolume;
    private double payload;
    private Boolean isCurrentVehicle;
}
