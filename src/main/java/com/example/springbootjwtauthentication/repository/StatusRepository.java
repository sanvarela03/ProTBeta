package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.Enum.EStatus;
import com.example.springbootjwtauthentication.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<Status> findByName(EStatus name);
}
