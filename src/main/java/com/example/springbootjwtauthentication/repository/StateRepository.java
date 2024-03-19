package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    boolean existsByName(String name);

    @Query(
            value = "SELECT s FROM State s WHERE s.country.name = ?1 AND s.name = ?2"
    )
    Optional<State> findFiltered(String country, String state);
}
