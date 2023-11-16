package com.example.springbootjwtauthentication.controller;

import com.example.springbootjwtauthentication.payload.request.AddAddressRequest;
import com.example.springbootjwtauthentication.security.jwt.JwtUtils;
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
    private JwtUtils jwtUtils;

    @PutMapping("/add-address")
    @PreAuthorize("hasRole('PRODUCER')")
    public ResponseEntity<?> addAddress(
            HttpServletRequest httpServletRequest,
            @Valid @RequestBody AddAddressRequest request) {

        log.info("S_HttpServeletRequest: {}", httpServletRequest);
        log.info("S_Header: {}", httpServletRequest.getHeaderNames());
        log.info("S_Boddy: {}", request);

        return ResponseEntity.ok("Yow");
    }

}
