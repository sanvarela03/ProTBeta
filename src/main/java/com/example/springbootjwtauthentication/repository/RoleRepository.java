package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.ERole;
import com.example.springbootjwtauthentication.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
