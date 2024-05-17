package com.example.springbootjwtauthentication.repository;


import com.example.springbootjwtauthentication.model.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {
    Optional<Producer> findByUsername(String username);

    @Query(
            "SELECT P FROM Producer P where P.currentAddress.city.name = ?1"
    )
    Optional<List<Producer>> findAllByCity(String city);



}
