package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.OrderStatus;
import com.example.springbootjwtauthentication.model.PK.OrderStatusPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, OrderStatusPK> {

    Optional<List<OrderStatus>> findAllByOrderId(Long id);
}
