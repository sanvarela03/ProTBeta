package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.Enum.ERate;
import com.example.springbootjwtauthentication.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    Optional<Rate> findByName(ERate name);
}
