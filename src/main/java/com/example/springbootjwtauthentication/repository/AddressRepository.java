package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
