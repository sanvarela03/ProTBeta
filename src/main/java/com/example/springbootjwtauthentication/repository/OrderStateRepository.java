package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.OrderState;
import com.example.springbootjwtauthentication.model.PK.OrderStatePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStateRepository extends JpaRepository<OrderState, OrderStatePK> {
}
