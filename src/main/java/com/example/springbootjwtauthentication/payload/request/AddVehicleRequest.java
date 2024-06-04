package com.example.springbootjwtauthentication.payload.request;

import com.example.springbootjwtauthentication.model.Transporter;
import com.example.springbootjwtauthentication.model.Vehicle;
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
    private String plate;
    @NotBlank
    private String fuelType;
    private double fuelConsumption;
    private String fuelConsumptionUnit;
    private double cargoVolume;
    private double payload;
    private Boolean isCurrentVehicle;

    public Vehicle getVehicle(Transporter transporter) {
        Vehicle vehicle = new Vehicle();

        vehicle.setBrand(this.getBrand());
        vehicle.setModel(this.getModel());
        vehicle.setYear(this.getYear());
        vehicle.setVin(this.getVin());
        vehicle.setPlate(this.getPlate());
        vehicle.setFuelType(this.getFuelType());
        vehicle.setFuelConsumption(this.getFuelConsumption());
        vehicle.setFuelConsumptionUnit(this.getFuelConsumptionUnit());
        vehicle.setCargoVolume(this.getCargoVolume());
        vehicle.setPayload(this.getPayload());
        vehicle.setTransporter(transporter);
        return vehicle;
    }
}
