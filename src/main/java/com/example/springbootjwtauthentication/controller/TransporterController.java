package com.example.springbootjwtauthentication.controller;

import com.example.springbootjwtauthentication.model.Transporter;
import com.example.springbootjwtauthentication.payload.request.AddAddressRequest;
import com.example.springbootjwtauthentication.payload.request.TransporterAnswerRequest;
import com.example.springbootjwtauthentication.service.api.AddressServiceApi;
import com.example.springbootjwtauthentication.service.implementations.JWTService;
import com.example.springbootjwtauthentication.service.implementations.TransporterAssignmentManager;
import com.example.springbootjwtauthentication.service.implementations.TransporterService;
import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/transporter")
@Slf4j
public class TransporterController {

    @Autowired
    private TransporterAssignmentManager assignmentManager;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private TransporterService transporterService;

    /**
     * http://localhost:8095/api/transporter/accept-order
     */
    @PostMapping("/accept-order")
    @PreAuthorize("hasRole('TRANSPORTER')")
    public ResponseEntity<?> acceptOrder(HttpServletRequest http, @RequestBody TransporterAnswerRequest request) throws FirebaseMessagingException, ParseException {
        log.info("HOLA CONCHE SU MARE");

        Transporter transporter = transporterService.getTransporterByUsername(jwtService.extractUsername(http));
        assignmentManager.notify(request, transporter);
        if (request.isAccepted()) {
            return ResponseEntity.ok("Se ha notificado al comprador y al productor");
        } else {
            return ResponseEntity.ok("Vale ok no hay problema :) después no andes chingando");
        }
    }
}
