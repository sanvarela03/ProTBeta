package com.example.springbootjwtauthentication.service.api;

import com.example.springbootjwtauthentication.model.Transporter;
import com.example.springbootjwtauthentication.model.Vehicle;
import com.example.springbootjwtauthentication.payload.request.AddVehicleRequest;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.service.implementations.JWTService;
import com.example.springbootjwtauthentication.service.implementations.TransporterService;
import com.example.springbootjwtauthentication.service.implementations.VehicleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceApi {

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private TransporterService transporterService;

    public ResponseEntity<MessageResponse> addVehicle(AddVehicleRequest request, HttpServletRequest http) {
        Transporter transporter = transporterService.getByUsername(jwtService.extractUsername(http));
        Vehicle vehicle = getVehicle(request, transporter);

        vehicleService.addVehicle(vehicle);

        if (request.getIsCurrentVehicle()) {
            transporter.setCurrentVehicle(vehicle);
            transporterService.saveTransporter(transporter);
        }
        return ResponseEntity.ok(new MessageResponse("Vehicle added"));
    }

    private static Vehicle getVehicle(AddVehicleRequest request, Transporter transporter) {
        Vehicle vehicle = new Vehicle();

        vehicle.setBrand(request.getBrand());
        vehicle.setModel(request.getModel());
        vehicle.setYear(request.getYear());
        vehicle.setVin(request.getVin());
        vehicle.setFuelType(request.getFuelType());
        vehicle.setFuelConsumption(request.getFuelConsumption());
        vehicle.setFuelConsumptionUnit(request.getFuelConsumptionUnit());
        vehicle.setCargoVolume(request.getCargoVolume());
        vehicle.setPayload(request.getPayload());
        vehicle.setOwner(transporter);
        return vehicle;
    }
}
