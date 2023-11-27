package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.OrderProduct;
import com.example.springbootjwtauthentication.model.PK.OrderProductPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductPK> {
}
