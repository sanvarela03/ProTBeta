package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.Address;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<List<Address>> findAllByUser_Id(Long id);

    @Transactional
    Integer removeById(Long id);
}
