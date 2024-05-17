package com.example.springbootjwtauthentication.controller;

import com.example.springbootjwtauthentication.payload.request.AddAddressRequest;
import com.example.springbootjwtauthentication.payload.request.UpdateAddressRequest;
import com.example.springbootjwtauthentication.service.api.AddressServiceApi;
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
@RequestMapping("/v1/api/users")
@Slf4j
@SecurityRequirement(name ="jwt-auth")
public class AddressController {
    @Autowired
    private AddressServiceApi addressServiceApi;

    @GetMapping("/{userId}/address")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllAddress(
            @PathVariable Long userId,
            HttpServletRequest http
    ) {
        return addressServiceApi.getAllAddress(userId, http);
    }

    @PostMapping("/address")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addAddress(HttpServletRequest http, @Valid @RequestBody AddAddressRequest request) {
        return addressServiceApi.addAddress(http, request);
    }

    @PutMapping("/{userId}/address/{addressId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId,
            @Valid @RequestBody UpdateAddressRequest request,
            HttpServletRequest http
    ) {
        return addressServiceApi.updateAddress(userId, addressId, request, http);
    }

    @DeleteMapping("/{userId}/address/{addressId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId,
            HttpServletRequest http
    ) {
        log.info("Address Controller | deleteAddress | addressId : {}", addressId);
        return addressServiceApi.deleteAddress(userId, addressId, http);
    }
}
