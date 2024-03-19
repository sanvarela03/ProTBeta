package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.OrderRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRateRepository extends JpaRepository<OrderRate, Long> {
}
