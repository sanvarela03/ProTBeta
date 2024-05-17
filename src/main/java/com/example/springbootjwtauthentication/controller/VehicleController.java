package com.example.springbootjwtauthentication.controller;

import com.example.springbootjwtauthentication.payload.request.AddVehicleRequest;
import com.example.springbootjwtauthentication.payload.request.UpdateVehicleRequest;
import com.example.springbootjwtauthentication.service.api.VehicleServiceApi;
import com.example.springbootjwtauthentication.service.implementations.VehicleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/api/transporters")
@Slf4j
@SecurityRequirement(name ="jwt-auth")
public class VehicleController {
    @Autowired
    private VehicleServiceApi vehicleServiceApi;

    @PostMapping("/{transporterId}/vehicles")
    @PreAuthorize("hasRole('TRANSPORTER')")
    public ResponseEntity<?> createVehicle(@PathVariable Long transporterId, @Valid @RequestBody AddVehicleRequest request, HttpServletRequest http) {
        return vehicleServiceApi.addVehicle(request, http);
    }

    @PutMapping("/{transporterId}/vehicles/{vehicleId}")
    @PreAuthorize("hasRole('TRANSPORTER')")
    public ResponseEntity<?> updateVehicle(
            @PathVariable Long transporterId,
            @PathVariable Long vehicleId,
            @RequestBody UpdateVehicleRequest request) {
        return vehicleServiceApi.updateVehicle(transporterId, vehicleId, request);
    }


    @DeleteMapping("/{transporterId}/vehicles/{vehicleId}")
    @PreAuthorize("hasRole('TRANSPORTER')")
    public ResponseEntity<?> deleteVehicle(
            @PathVariable Long transporterId,
            @PathVariable Long vehicleId
    ) {
        return vehicleServiceApi.deleteVehicle(transporterId, vehicleId);
    }

    @GetMapping("/{transporterId}/vehicles")
    @PreAuthorize("hasRole('TRANSPORTER')")
    public ResponseEntity<?> getAllVehicles(@PathVariable Long transporterId) {
        return vehicleServiceApi.getAllVehicles(transporterId);
    }
}
