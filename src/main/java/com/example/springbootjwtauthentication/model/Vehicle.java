package com.example.springbootjwtauthentication.model;

import com.example.springbootjwtauthentication.payload.request.UpdateVehicleRequest;
import com.example.springbootjwtauthentication.payload.response.AddressResponse;
import com.example.springbootjwtauthentication.payload.response.VehicleResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "vehicles")
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
    private String plate;


    private String fuelType;
    private double fuelConsumption;
    private String fuelConsumptionUnit; // L/100Km
    private double cargoVolume;
    private double payload;
    @ManyToOne
    @JoinColumn(
            name = "transporter_id",
            referencedColumnName = "id"
    )
    private Transporter transporter;

    @Serial
    private static final long serialVersionUID = 1L;


    public VehicleResponse toVehicleResponse() {
        return VehicleResponse.builder()
                .id(this.getId())
                .brand(this.getBrand())
                .model(this.getModel())
                .year(this.getYear())
                .vin(this.getVin())
                .plate(this.getPlate())
                .fuelType(this.getFuelType())
                .fuelConsumption(this.getFuelConsumption())
                .fuelConsumptionUnit(this.getFuelConsumptionUnit())
                .cargoVolume(this.getCargoVolume())
                .payload(this.getPayload())
                .transporterId(this.getTransporter().getId())
                .build();
    }

    public static List<VehicleResponse> getVehicleResponseList(List<Vehicle> vehicleList) {
        List<VehicleResponse> vehicleResponseList = new ArrayList<>();

        vehicleList.forEach(
                vehicle -> {
                    vehicleResponseList.add(vehicle.toVehicleResponse());
                }
        );
        return vehicleResponseList;
    }

    public void update(UpdateVehicleRequest request) {
        this.setBrand(request.getBrand());
        this.setModel(request.getModel());
        this.setYear(request.getYear());
        this.setFuelType(request.getFuelType());
        this.setFuelConsumption(request.getFuelConsumption());
        this.setFuelConsumptionUnit(request.getFuelConsumptionUnit());
        this.setCargoVolume(request.getCargoVolume());
        this.setPayload(request.getPayload());
    }
}
