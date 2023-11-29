package com.example.springbootjwtauthentication.service.interfaces;

import com.example.springbootjwtauthentication.payload.request.SignupRequest;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;

public interface SignUp {
    <T> ResponseEntity<T> doSignUp(SignupRequest request);
}
