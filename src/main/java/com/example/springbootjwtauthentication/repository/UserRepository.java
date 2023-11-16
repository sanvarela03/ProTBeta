package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    @Modifying
    @Transactional
    @Query(
            value = "UPDATE User u SET u.password = ?1 WHERE u.id = ?2"
    )
    Optional<?> updatePasswordById(String newPassword, String userId);
    @Modifying
    @Transactional
    @Query(
            value = "UPDATE User u SET u.verificationCode = null, u.verificationCodeTimestamp = null WHERE u.id = ?1"
    )
    Optional<?> resetVerificationCodeById(Long id);

    @Modifying
    @Transactional
    @Query(
            value = "SELECT u FROM User u WHERE u.email = ?1 AND u.verificationCode is null AND u.verificationCodeTimestamp is null"
    )
    Optional<User> findByEmailSecure(String email);
}
