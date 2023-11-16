package com.example.springbootjwtauthentication.controller.service.interfaces;

import com.example.springbootjwtauthentication.payload.request.TokenRefreshRequest;
import org.springframework.http.ResponseEntity;

public interface ForgotPasswordProcess {
    <T> ResponseEntity<T> doRefreshToken(TokenRefreshRequest request);
}
