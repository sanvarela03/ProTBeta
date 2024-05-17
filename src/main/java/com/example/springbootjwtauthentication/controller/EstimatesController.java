package com.example.springbootjwtauthentication.controller;

import com.example.springbootjwtauthentication.service.api.OrderServiceApi;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/api/estimates")
@Slf4j
@SecurityRequirement(name ="jwt-auth")
public class EstimatesController {
    @Autowired
    private OrderServiceApi orderServiceApi;



    /**
    * Estima la distancia
    */
    @GetMapping("/shipping-cost/{producerId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> estimatesShippingCost(@PathVariable Long producerId, HttpServletRequest http) throws ExecutionException, InterruptedException {
        return orderServiceApi.calculateShippingCostRange(producerId, http);
    }
}
