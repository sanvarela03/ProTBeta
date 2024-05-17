package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.Order;
import com.example.springbootjwtauthentication.model.OrderProduct;
import com.example.springbootjwtauthentication.model.PK.OrderProductPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductPK> {

    Optional<List<OrderProduct>> findAllByOrderId(Long id);
}
