package com.example.springbootjwtauthentication.controller;


import com.example.springbootjwtauthentication.payload.request.AddProductRequest;
import com.example.springbootjwtauthentication.payload.request.UpdateProductRequest;
import com.example.springbootjwtauthentication.service.api.ProductServiceApi;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductServiceApi productServiceApi;

    @GetMapping("/products")
    @PreAuthorize("hasRole('PRODUCER')")
    public ResponseEntity<?> getAllProducts(HttpServletRequest http) {
        return productServiceApi.getAllProducts(http);
    }
    @PostMapping("/add-product")
    @PreAuthorize("hasRole('PRODUCER')")
    public ResponseEntity<?> addProduct(HttpServletRequest http, @Valid @RequestBody AddProductRequest request) {
        log.info("Solicitud para agregar producto recibida");
        return productServiceApi.addProduct(http, request);
    }
    @PutMapping("/update-product")
    @PreAuthorize("hasRole('PRODUCER')")
    public ResponseEntity<?> updateProduct(HttpServletRequest http, @Valid @RequestBody UpdateProductRequest request) {
        return productServiceApi.updateProduct(http, request);
    }
}
