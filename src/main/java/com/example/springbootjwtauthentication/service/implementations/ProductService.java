package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.Product;
import com.example.springbootjwtauthentication.model.ProductImage;
import com.example.springbootjwtauthentication.repository.ProducerRepository;
import com.example.springbootjwtauthentication.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("No se encontró el producto con Id: " + productId));
    }

    public List<Product> getAllProductsByProducerId(Long producerId) {
        return productRepository.findByProducer_Id(producerId)
                .orElseThrow(() -> new RuntimeException("No se encontró los productos para el productor ocn Id:" + producerId));
    }
    public List<Product> getAllAvailableProductsByProducerId(Long producerId) {
        return productRepository.findAvailableProductsByProducerId(producerId)
                .orElseThrow(() -> new RuntimeException("No se encontró los productos para el productor ocn Id:" + producerId));
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    public List<ProductImage> getProductImages(Long productId) {
//        TODO
        return null;
    }

    public ProductImage saveProductImage(Long productId, ProductImage productImage) {
        return null;
    }
}
