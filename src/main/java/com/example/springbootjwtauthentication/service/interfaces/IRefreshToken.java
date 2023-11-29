package com.example.springbootjwtauthentication.service.interfaces;

import com.example.springbootjwtauthentication.payload.request.TokenRefreshRequest;
import org.springframework.http.ResponseEntity;

public interface IRefreshToken {
    <T> ResponseEntity<T> doRefreshToken(TokenRefreshRequest request);
}
