package com.example.springbootjwtauthentication.controller;

import com.example.springbootjwtauthentication.payload.request.AddAddressRequest;
import com.example.springbootjwtauthentication.service.api.AddressServiceApi;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    @Autowired
    private AddressServiceApi addressServiceApi;

    @PostMapping("/add-address")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addAddress(HttpServletRequest http, @Valid @RequestBody AddAddressRequest request) {
        return addressServiceApi.addAddress(http, request);
    }
}
