package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.Address;
import com.example.springbootjwtauthentication.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<List<Order>> findAllByCustomer_Id(Long id);

    Optional<List<Order>> findAllByProducer_Id(Long id);

    Optional<List<Order>> findAllByTransporter_Id(Long id);

    Optional<Order> findById(Long id);
}

