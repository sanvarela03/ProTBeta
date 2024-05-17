package com.example.springbootjwtauthentication.service.api;

import com.example.springbootjwtauthentication.model.Producer;
import com.example.springbootjwtauthentication.model.Product;
import com.example.springbootjwtauthentication.payload.request.AddProductRequest;
import com.example.springbootjwtauthentication.payload.request.UpdateProductRequest;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.payload.response.ProductsResponse;
import com.example.springbootjwtauthentication.repository.ProducerRepository;
import com.example.springbootjwtauthentication.repository.ProductRepository;
import com.example.springbootjwtauthentication.security.jwt.JwtUtils;
import com.example.springbootjwtauthentication.service.implementations.JWTService;
import com.example.springbootjwtauthentication.service.implementations.ProducerService;
import com.example.springbootjwtauthentication.service.implementations.ProductService;
import com.example.springbootjwtauthentication.service.serializer.ProductSerializerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceApi {
    @Autowired
    private ProducerService producerService;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private ProductService productService;

    public ResponseEntity<ProductsResponse> getAllProducts(HttpServletRequest http) {
        String username = jwtService.extractUsername(http);
        Producer producer = producerService.getProducerByUsername(username);
        List<Product> products = productService.getAllProductsByProducerId(producer.getId());
        return ResponseEntity.ok(new ProductsResponse(products));
    }

    public ResponseEntity<MessageResponse> addProduct(HttpServletRequest http, AddProductRequest request) {
        String username = jwtService.extractUsername(http);
        Producer producer = producerService.getProducerByUsername(username);
        Product newProduct = request.toProduct(producer);
        productService.saveProduct(newProduct);
        return ResponseEntity.ok(new MessageResponse("Producto guardado correctamente"));
    }

    public ResponseEntity<MessageResponse> updateProduct(
            Long productId,
            UpdateProductRequest request,
            HttpServletRequest http
    ) {
        String username = jwtService.extractUsername(http);
        Producer producer = producerService.getProducerByUsername(username);
        Product product = productService.getProductById(productId);
        if (!isOwner(product, producer)) {
            return ResponseEntity.badRequest().body(new MessageResponse("El productor no es el propietario del producto"));
        }
        product.update(request);
        productService.saveProduct(product);
        return ResponseEntity.ok(new MessageResponse("Producto actualizado correctamente"));
    }

    public ResponseEntity<MessageResponse> deleteProduct(
            Long productId,
            HttpServletRequest http
    ) {
        String username = jwtService.extractUsername(http);
        Producer producer = producerService.getProducerByUsername(username);
        Product product = productService.getProductById(productId);
        if (!isOwner(product, producer)) {
            return ResponseEntity.badRequest().body(new MessageResponse("El productor no es el propietario del producto"));
        }
        productService.deleteProduct(productId);
        return ResponseEntity.ok(new MessageResponse("Producto eliminado correctamente"));
    }

    private boolean isOwner(Product product, Producer producer) {
        return product.getProducer().equals(producer);
    }
}
