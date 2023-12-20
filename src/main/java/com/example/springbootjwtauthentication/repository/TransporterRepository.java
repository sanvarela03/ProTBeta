package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.Producer;
import com.example.springbootjwtauthentication.model.Transporter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransporterRepository extends JpaRepository<Transporter, Long> {
    Optional<Transporter> findByUsername(String username);
}
