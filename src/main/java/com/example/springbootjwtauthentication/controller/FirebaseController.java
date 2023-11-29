package com.example.springbootjwtauthentication.controller;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth/firebase")
public class FirebaseController {

    @Autowired
    private FirebaseMessaging firebaseMessaging;


    @PostMapping("/clients/{registrationToken}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> postToClient(@RequestBody String message, @PathVariable("registrationToken") String registrationToken) throws FirebaseMessagingException {
        Message msg = Message.builder()
                .setToken(registrationToken)
                .putData("body", message)
                .build();

        String id = firebaseMessaging.send(msg);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("El id es: "+id);
    }

}
