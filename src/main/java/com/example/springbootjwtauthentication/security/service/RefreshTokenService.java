package com.example.springbootjwtauthentication.security.service;

import com.example.springbootjwtauthentication.exception.TokenRefreshException;
import com.example.springbootjwtauthentication.model.RefreshToken;
import com.example.springbootjwtauthentication.model.User;
import com.example.springbootjwtauthentication.repository.RefreshTokenRepository;
import com.example.springbootjwtauthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${santi.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken getByUser(User user) {
        return refreshTokenRepository.findByUser(user).orElseThrow(() -> new RuntimeException("User not found in RefreshTokenService with id : " + user.getId()));
    }

    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token); //TODO : < - esto no esta funcionando ??
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    public Boolean existsByUser(User user) {
        return refreshTokenRepository.existsByUser(user).orElseThrow(() -> new RuntimeException("Error in RefreshTokenService with user: " + user.getId()));
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }
}
