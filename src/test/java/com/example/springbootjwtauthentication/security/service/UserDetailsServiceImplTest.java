package com.example.springbootjwtauthentication.security.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDetailsServiceImplTest {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsername(){
        var user = userDetailsService.loadUserByUsername("mod");

        System.out.println("user = " + user);
    }
}