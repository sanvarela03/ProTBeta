package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.RefreshToken;
import com.example.springbootjwtauthentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);

    Optional<Boolean> existsByUser(User user);

    @Modifying
    int deleteByUser(User user);
}
