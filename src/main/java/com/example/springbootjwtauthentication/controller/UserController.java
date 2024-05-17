package com.example.springbootjwtauthentication.controller;

import com.example.springbootjwtauthentication.payload.request.AddAddressRequest;
import com.example.springbootjwtauthentication.payload.request.UpdateAddressRequest;
import com.example.springbootjwtauthentication.payload.request.UpdateFirebaseTokenRequest;
import com.example.springbootjwtauthentication.payload.request.UserInfoRequest;
import com.example.springbootjwtauthentication.service.api.AddressServiceApi;
import com.example.springbootjwtauthentication.service.api.UserServiceApi;
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
@SecurityRequirement(name = "jwt-auth")
public class UserController {
    @Autowired
    private UserServiceApi userServiceApi;

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAccount(
            @PathVariable Long userId
    ) {
        return userServiceApi.getUserAccount(userId);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateAccount(
            @PathVariable Long userId,
            @RequestBody UserInfoRequest request
    ) {
        return userServiceApi.updateUserAccount(userId, request);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteAccount(
            @PathVariable Long userId
    ) {
        return userServiceApi.deleteUserAccount(userId);
    }


    @GetMapping("/{userId}/orders")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllOrders(
            @PathVariable Long userId,
            HttpServletRequest http
    ) {
        return userServiceApi.getAllOrderInfo(userId);
    }

    @PutMapping("/{userId}/firebase-token")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateFirebaseToken(
            @PathVariable Long userId,
            @RequestBody UpdateFirebaseTokenRequest request
    ) {
        return userServiceApi.updateFirebaseToken(userId, request);
    }
}
