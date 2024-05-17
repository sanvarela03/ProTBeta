package com.example.springbootjwtauthentication.service.api;

import com.example.springbootjwtauthentication.model.Transporter;
import com.example.springbootjwtauthentication.model.Vehicle;
import com.example.springbootjwtauthentication.payload.request.AddVehicleRequest;
import com.example.springbootjwtauthentication.payload.request.UpdateVehicleRequest;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.payload.response.VehicleResponse;
import com.example.springbootjwtauthentication.service.implementations.JWTService;
import com.example.springbootjwtauthentication.service.implementations.TransporterService;
import com.example.springbootjwtauthentication.service.implementations.VehicleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.springbootjwtauthentication.model.Vehicle.getVehicleResponseList;

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
        return ResponseEntity.ok(new MessageResponse("Vehiculo agregado correctamente"));
    }

    public ResponseEntity<MessageResponse> updateVehicle(Long transporterId, Long vehicleId, UpdateVehicleRequest request) {
        Transporter transporter = transporterService.getById(transporterId);
        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);

        vehicle.update(request);
        vehicleService.save(vehicle);

        if (request.getIsCurrentVehicle()) {
            transporter.setCurrentVehicle(vehicle);
            transporterService.saveTransporter(transporter);
        }
        return ResponseEntity.ok(new MessageResponse("Vehiculo actualizado correctamente"));
    }


    public ResponseEntity<MessageResponse> deleteVehicle(Long transporterId, Long vehicleId) {
        Transporter transporter = transporterService.getById(transporterId);

        if (transporter.getCurrentVehicle().getId().equals(vehicleId)) {
            transporter.setCurrentVehicle(null);
            transporterService.saveTransporter(transporter);
        }

        vehicleService.delete(vehicleId);
        return ResponseEntity.ok(new MessageResponse("Vehiculo eliminado correctamente"));
    }


    public ResponseEntity<List<VehicleResponse>> getAllVehicles(Long transporterId) {
        List<Vehicle> vehicleList = vehicleService.getAllVehiclesByTransporterId(transporterId);
        List<VehicleResponse> vehicleResponseList = getVehicleResponseList(vehicleList);

        return ResponseEntity.ok(vehicleResponseList);
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
        vehicle.setTransporter(transporter);
        return vehicle;
    }
}
