package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.Customer;
import com.example.springbootjwtauthentication.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUsername(String username);
}
