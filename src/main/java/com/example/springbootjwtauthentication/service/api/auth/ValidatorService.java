package com.example.springbootjwtauthentication.service.api.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class ValidatorService {

    //TODO - Validar que el usuario que realiza la solicitud
    public Boolean validate(HttpServletRequest http, Long userId) {
        return false;
    }
}
