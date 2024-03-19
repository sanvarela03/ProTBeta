package com.example.springbootjwtauthentication.controller;

import com.example.springbootjwtauthentication.payload.request.ProducerAnswerRequest;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.service.api.AddressServiceApi;
import com.example.springbootjwtauthentication.payload.request.AddAddressRequest;
import com.example.springbootjwtauthentication.service.api.ProducerServiceApi;
import com.example.springbootjwtauthentication.service.implementations.JWTService;
import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/api/producers")
@Slf4j
public class ProducerController {
    @Autowired
    private ProducerServiceApi producerServiceApi;

    @Autowired
    private JWTService jwtService;

    /**
     * http://localhost:8095/v1/api/producers/{userId}/orders/{orderId}?accepted=true
     */
    @PostMapping("/{userId}/orders/{orderId}")
    @PreAuthorize("hasRole('PRODUCER')")
    public ResponseEntity<?> acceptOrder(
            @PathVariable Long userId,
            @PathVariable Long orderId,
            @RequestParam boolean accepted,
            @RequestBody ProducerAnswerRequest request,
            HttpServletRequest http
    ) throws FirebaseMessagingException {
        Long userIdFromToken = jwtService.extractUserId(http);

        if (Objects.equals(userIdFromToken, userId)) {
            return producerServiceApi.acceptOrder(http, request);
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Not allowed, expected: " + userIdFromToken + " passed: " + userId));
        }
    }




    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('PRODUCER')")
    public ResponseEntity<?> getProducerInfo(@PathVariable Long userId) {
        return producerServiceApi.getProducer(userId);
    }
}
