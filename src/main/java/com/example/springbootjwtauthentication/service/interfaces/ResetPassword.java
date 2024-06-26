package com.example.springbootjwtauthentication.service.interfaces;

import com.example.springbootjwtauthentication.payload.request.ResetPassRequest;
import org.springframework.http.ResponseEntity;

public interface ResetPassword {
    <T> ResponseEntity<T> doReset(ResetPassRequest request);
}
