package com.example.springbootjwtauthentication.controller;


import com.example.springbootjwtauthentication.payload.request.AddProductRequest;
import com.example.springbootjwtauthentication.payload.request.UpdateProductRequest;
import com.example.springbootjwtauthentication.service.api.ProductServiceApi;
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
@RequestMapping("/v1/api/products")
@Slf4j
@SecurityRequirement(name ="jwt-auth")
public class ProductController {
    @Autowired
    private ProductServiceApi productServiceApi;

    @GetMapping
    @PreAuthorize("hasRole('PRODUCER')")
    public ResponseEntity<?> getAllProducts(HttpServletRequest http) {
        return productServiceApi.getAllProducts(http);
    }

    @PostMapping
    @PreAuthorize("hasRole('PRODUCER')")
    public ResponseEntity<?> addProduct(HttpServletRequest http, @Valid @RequestBody AddProductRequest request) {
        log.info("Solicitud para agregar producto recibida");
        return productServiceApi.addProduct(http, request);
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasRole('PRODUCER')")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateProductRequest request,
            HttpServletRequest http
    ) {
        return productServiceApi.updateProduct(productId, request, http);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('PRODUCER')")
    public ResponseEntity<?> deleteProduct(
            @PathVariable Long productId,
            HttpServletRequest http
    ) {
        return productServiceApi.deleteProduct(productId, http);
    }
}
