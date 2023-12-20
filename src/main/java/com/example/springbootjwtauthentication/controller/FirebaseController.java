package com.example.springbootjwtauthentication.controller;

import com.example.springbootjwtauthentication.payload.request.NotificationRequest;
import com.google.firebase.messaging.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth/firebase")
public class FirebaseController {

    @Autowired
    private FirebaseMessaging firebaseMessaging;


    /**
     * http://localhost:8095/api/auth/firebase/clients/{}
     */
    @PostMapping("/clients/{registrationToken}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> postToClient(@RequestBody @Valid NotificationRequest notificationRequest, @PathVariable("registrationToken") String registrationToken) throws FirebaseMessagingException {
//        Message msg = Message.builder()
//                .setToken(registrationToken)
//                .putData("body", message)
//                .build();
        Map<String, String> data = new HashMap<>();
        data.put("body", notificationRequest.getBody());
        data.put("additionalData", notificationRequest.getMessage());

        Notification notification = Notification.builder()
                .setTitle(notificationRequest.getTitle())
                .setBody(notificationRequest.getBody())
                .build();

        Message msg = Message.builder()
                .setToken(registrationToken)
                .setNotification(notification)
                .putAllData(data)
                .setAndroidConfig(AndroidConfig.builder()
                        .setNotification(AndroidNotification.builder()
                                .setSound("default")
                                .setClickAction("YOUR_CLICK_ACTION")
                                .build())
                        .build())
                .build();

        String id = firebaseMessaging.send(msg);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("El id es: " + id);
    }

}
