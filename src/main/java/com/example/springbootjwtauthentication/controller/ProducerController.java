package com.example.springbootjwtauthentication.controller;

import com.example.springbootjwtauthentication.controller.service.implementations.ProductService;
import com.example.springbootjwtauthentication.controller.service.implementations.UserService;
import com.example.springbootjwtauthentication.payload.request.AddAddressRequest;
import com.example.springbootjwtauthentication.payload.request.AddProductRequest;
import com.example.springbootjwtauthentication.payload.request.UpdateProductRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/producer")
@Slf4j
public class ProducerController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @PostMapping("/add-product")
    @PreAuthorize("hasRole('PRODUCER')")
    public ResponseEntity<?> addProduct(HttpServletRequest http, @Valid @RequestBody AddProductRequest request) {
        log.info("Solicitud para agregar producto recibida");
        return productService.addProduct(http, request);
    }

    @PutMapping("/update-product")
    @PreAuthorize("hasRole('PRODUCER')")
    public ResponseEntity<?> updateProduct(HttpServletRequest http, @Valid @RequestBody UpdateProductRequest request) {
        return productService.updateProduct(http, request);
    }

    @GetMapping("/products")
    @PreAuthorize("hasRole('PRODUCER')")
    public ResponseEntity<?> getProducts(HttpServletRequest http) {
        return productService.getAllProducts(http);
    }


    @PostMapping("/add-address")
    @PreAuthorize("hasRole('PRODUCER')")
    public ResponseEntity<?> addAddress(HttpServletRequest http, @Valid @RequestBody AddAddressRequest request) {
        return userService.addAddress(http, request);
    }
}
