package com.example.springbootjwtauthentication.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FirebaseConfigurationTest {


    @Autowired
    private GoogleCredentials googleCredentials;

    @Autowired
    private FirebaseApp firebaseApp;

    @Autowired
    private FirebaseMessaging firebaseMessaging;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void googleCredentialsTest(){
        System.out.println("Goolge credentials: "+ googleCredentials);
        System.out.println("Goolge firebaseApp: "+ firebaseApp);
        System.out.println("Goolge firebaseMessaging: "+ firebaseMessaging);
    }
}