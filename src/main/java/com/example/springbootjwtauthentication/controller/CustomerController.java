package com.example.springbootjwtauthentication.controller;

import com.example.springbootjwtauthentication.payload.request.OrderRequest;
import com.example.springbootjwtauthentication.service.api.CustomerServiceApi;
import com.example.springbootjwtauthentication.service.api.OrderServiceApi;
import com.google.firebase.messaging.FirebaseMessagingException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/api/customers")
@Slf4j
@SecurityRequirement(name ="jwt-auth")
public class CustomerController {

    @Autowired
    private OrderServiceApi orderServiceApi;

    @Autowired
    private CustomerServiceApi customerServiceApi;

    @PostMapping("/orders/order")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> addOrder(
            @Valid @RequestBody OrderRequest request,
            HttpServletRequest http
    ) throws FirebaseMessagingException, InterruptedException, ExecutionException {
        log.info("Solicitud para agregar orden recibida");
        return orderServiceApi.addNewOrder(http, request);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getCustomerInfo(@PathVariable Long userId) {
        return customerServiceApi.getCustomer(userId);
    }

    @GetMapping("/{userId}/orders")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getAllOrders(@PathVariable Long userId) {
        return customerServiceApi.getAllOrderInfo(userId);
    }



    @GetMapping("/{customerId}/search-producers")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getAllProducers(
            @PathVariable Long customerId,
            @RequestParam String city
    ) {
        return customerServiceApi.searchAllProducers();
    }

    @GetMapping("/{customerId}/search-producers/{producerId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getProducerDetail(
            @PathVariable Long customerId,
            @PathVariable Long producerId
    ) {
        return customerServiceApi.searchProducer(producerId);
    }
}
