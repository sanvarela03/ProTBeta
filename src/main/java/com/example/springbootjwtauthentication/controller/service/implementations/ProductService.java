package com.example.springbootjwtauthentication.controller.service.implementations;

import com.example.springbootjwtauthentication.model.Producer;
import com.example.springbootjwtauthentication.model.Product;
import com.example.springbootjwtauthentication.model.User;
import com.example.springbootjwtauthentication.payload.request.AddAddressRequest;
import com.example.springbootjwtauthentication.payload.request.AddProductRequest;
import com.example.springbootjwtauthentication.payload.request.UpdateProductRequest;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.payload.response.ProductsResponse;
import com.example.springbootjwtauthentication.repository.ProducerRepository;
import com.example.springbootjwtauthentication.repository.ProductRepository;
import com.example.springbootjwtauthentication.security.jwt.JwtUtils;
import com.example.springbootjwtauthentication.service.ProducerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<MessageResponse> addProduct(
            HttpServletRequest http,
            AddProductRequest request
    ) {
        Producer producer = findProducer(http);
        producer.getProducts().add(createNewProduct(request, producer));
        producerRepository.save(producer);

        return ResponseEntity.ok(
                MessageResponse.builder()
                        .message("Producto guardado correctamente")
                        .build()
        );
    }

    public ResponseEntity<MessageResponse> updateProduct(
            HttpServletRequest http,
            UpdateProductRequest request
    ) {
        Producer producer = findProducer(http);
        Product product = findProductById(request.getId());

        if (!producer.getProducts().contains(product)) {
            return ResponseEntity.badRequest()
                    .body(MessageResponse.builder()
                            .message("El productor no es el propietario del producto")
                            .build());
        }

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setUnitsAvailable(request.getUnitsAvailable());
        product.setAvailable(request.isAvailable());

        productRepository.save(product);

        return ResponseEntity.ok(
                MessageResponse.builder()
                        .message("Producto actualizado correctamente")
                        .build()
        );
    }

    public ResponseEntity<ProductsResponse> getAllProducts(HttpServletRequest http) {
        Producer producer = findProducer(http);
        return ResponseEntity.ok(
                ProductsResponse.builder()
                        .products(producer.getProducts())
                        .build()
        );
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("No se pudo encontrar el producto con ID: " + productId));
    }


    private Producer findProducer(HttpServletRequest http) {
        String username = jwtUtils.getUserNameFromJwtToken(http);
        log.info("S_Username: {}", username);
        return producerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No se econtró el productor con usuario: " + username));
    }

    private Product createNewProduct(AddProductRequest request, Producer producer) {
        return Product.builder()
                .name(request.getName())
                .unitsAvailable(request.getUnitsAvailable())
                .description(request.getDescription())
                .price(request.getPrice())
                .isAvailable(true)
                .owner(producer)
                .build();
    }
}
