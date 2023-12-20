package com.example.springbootjwtauthentication.controller;

import com.example.springbootjwtauthentication.payload.request.ProducerAnswerRequest;
import com.example.springbootjwtauthentication.service.api.AddressServiceApi;
import com.example.springbootjwtauthentication.payload.request.AddAddressRequest;
import com.example.springbootjwtauthentication.service.api.ProducerServiceApi;
import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/producer")
@Slf4j
public class ProducerController {
    @Autowired
    private ProducerServiceApi producerServiceApi;

    @PostMapping("/accept-order")
    @PreAuthorize("hasRole('PRODUCER')")
    public ResponseEntity<?> acceptOrder(HttpServletRequest http, @RequestBody ProducerAnswerRequest request) throws FirebaseMessagingException {
        return producerServiceApi.acceptOrder(http, request);
    }
}
