package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query(
            value = "SELECT c FROM City c WHERE c.state.country.name = ?1 AND c.state.name = ?2 AND  c.name = ?3"
    )
    Optional<City> findFiltered(String country, String state, String city);

    @Query(
            value = "SELECT CASE WHEN count(c) > 0 THEN true ELSE false END AS exists FROM City c WHERE c.state.country.name = ?1 AND c.state.name = ?2 AND c.name = ?3"
    )
    boolean existsFilteredByAll(String country, String state, String city);

    @Query(
            value = "SELECT CASE WHEN count(c) > 0 THEN true ELSE false END as exists FROM City c WHERE c.state.country.name = ?1 AND c.state.name = ?2"
    )
    boolean existsFilteredByCountryAndState(String country, String state);

    @Query(
            value = "SELECT CASE WHEN count(c) > 0 THEN true ELSE false END as exists FROM City c WHERE c.state.country.name = ?1"
    )
    boolean existsFilteredByCountry(String country);
}
