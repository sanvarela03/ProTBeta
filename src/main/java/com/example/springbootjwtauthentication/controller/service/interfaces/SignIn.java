package com.example.springbootjwtauthentication.controller.service.interfaces;

import com.example.springbootjwtauthentication.payload.request.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface SignIn {
    <T> ResponseEntity<T> doSignIn(LoginRequest request);
}
