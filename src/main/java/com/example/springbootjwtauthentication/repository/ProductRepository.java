package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.Producer;
import com.example.springbootjwtauthentication.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<List<Product>> findByProducer_Id(Long id);

    @Query("SELECT P FROM Product P WHERE P.producer.id = ?1 AND P.isAvailable = true ")
    Optional<List<Product>> findAvailableProductsByProducerId(Long id);
}
