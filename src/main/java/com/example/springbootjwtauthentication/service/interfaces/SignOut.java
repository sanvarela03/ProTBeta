package com.example.springbootjwtauthentication.service.interfaces;

import com.example.springbootjwtauthentication.payload.request.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface SignOut {
    <T> ResponseEntity<T> doSignOut();
}
