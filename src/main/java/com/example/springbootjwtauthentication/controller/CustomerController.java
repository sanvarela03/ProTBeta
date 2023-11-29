package com.example.springbootjwtauthentication.controller;

import com.example.springbootjwtauthentication.payload.request.OrderRequest;
import com.example.springbootjwtauthentication.service.api.OrderServiceApi;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
@Slf4j
public class CustomerController {

    @Autowired
    private OrderServiceApi orderServiceApi;

    @PostMapping("/add-order")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> addOrder(HttpServletRequest http, @Valid @RequestBody OrderRequest request) {
        log.info("Solicitud para agregar orden recibida");
        return orderServiceApi.addNewOrder(http, request);
    }
}
