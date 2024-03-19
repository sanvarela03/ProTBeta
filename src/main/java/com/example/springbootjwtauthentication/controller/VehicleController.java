package com.example.springbootjwtauthentication.controller;

import com.example.springbootjwtauthentication.payload.request.AddVehicleRequest;
import com.example.springbootjwtauthentication.service.api.VehicleServiceApi;
import com.example.springbootjwtauthentication.service.implementations.VehicleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/api/transporters/vehicles")
@Slf4j
public class VehicleController {
    @Autowired
    private VehicleServiceApi vehicleServiceApi;
    @PostMapping
    @PreAuthorize("hasRole('TRANSPORTER')")
    public ResponseEntity<?> createVehicle(@Valid @RequestBody AddVehicleRequest request, HttpServletRequest http) {
        return vehicleServiceApi.addVehicle(request, http);
    }


}
