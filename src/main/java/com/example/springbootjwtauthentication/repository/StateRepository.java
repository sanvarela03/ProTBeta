package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.Enum.EState;
import com.example.springbootjwtauthentication.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    Optional<State> findByName(EState name);
}
