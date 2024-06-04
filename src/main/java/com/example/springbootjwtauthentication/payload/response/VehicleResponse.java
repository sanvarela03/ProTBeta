package com.example.springbootjwtauthentication.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleResponse {
    private Long id;
    private String brand;
    private String model;
    private String year;
    private String vin;
    private String plate;
    private String fuelType;
    private double fuelConsumption;
    private String fuelConsumptionUnit;
    private double cargoVolume;
    private double payload;
    private Long transporterId;
}
