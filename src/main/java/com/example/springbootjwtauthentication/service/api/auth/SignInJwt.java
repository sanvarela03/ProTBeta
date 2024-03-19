package com.example.springbootjwtauthentication.service.api.auth;

import com.example.springbootjwtauthentication.model.User;
import com.example.springbootjwtauthentication.repository.UserRepository;
import com.example.springbootjwtauthentication.service.interfaces.SignIn;
import com.example.springbootjwtauthentication.model.RefreshToken;
import com.example.springbootjwtauthentication.payload.request.LoginRequest;

import com.example.springbootjwtauthentication.payload.response.JwtResponse;
import com.example.springbootjwtauthentication.security.jwt.JwtUtils;
import com.example.springbootjwtauthentication.security.service.RefreshTokenService;
import com.example.springbootjwtauthentication.security.service.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SignInJwt implements SignIn {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<JwtResponse> doSignIn(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new RuntimeException("User not found in SignInJwt, Id" + userDetails.getId()));


        log.info("refresh check: ");
        log.info("refreshTokenService.existsByUser(user) ---> {}", refreshTokenService.existsByUser(user));

        if (refreshTokenService.existsByUser(user)) {
            RefreshToken old = refreshTokenService.getByUser(user);
            if (old.getExpiryDate().compareTo(Instant.now()) < 0) {
                refreshTokenService.deleteByUserId(userDetails.getId()); //TODO : < - esto no esta funcionando ??
            }
        }

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);


        return ResponseEntity.ok(
                JwtResponse.builder()
                        .token(jwt)
                        .type("Bearer")
                        .refreshToken(refreshToken.getToken())
                        .id(userDetails.getId())
                        .username(userDetails.getUsername())
                        .email(userDetails.getEmail())
                        .roles(roles)
                        .build()
        );
    }
}
