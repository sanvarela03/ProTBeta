package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JWTService {

    @Autowired
    private JwtUtils jwtUtils;

    public String extractUsername(HttpServletRequest http) {
        return jwtUtils.getUserNameFromJwtToken(http);
    }

}
