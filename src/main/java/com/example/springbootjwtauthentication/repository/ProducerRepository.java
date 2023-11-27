package com.example.springbootjwtauthentication.repository;


import com.example.springbootjwtauthentication.model.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {
    Optional<Producer> findByUsername(String username);
}
