package com.example.springbootjwtauthentication.controller;

import com.example.springbootjwtauthentication.model.Transporter;
import com.example.springbootjwtauthentication.payload.request.TransporterAnswerRequest;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.service.implementations.JWTService;
import com.example.springbootjwtauthentication.service.implementations.TransporterAssignmentManager;
import com.example.springbootjwtauthentication.service.implementations.TransporterService;
import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/api/transporters")
@Slf4j
public class TransporterController {
    @Autowired
    private TransporterAssignmentManager assignmentManager;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private TransporterService transporterService;

    /**
     * http://localhost:8095/v1/api/transporters/{userId}/orders/{orderId}?accepted=true
     */
    @PostMapping("/{userId}/orders/{orderId}")
    @PreAuthorize("hasRole('TRANSPORTER')")
    public ResponseEntity<?> acceptOrder(
            @PathVariable Long userId,
            @PathVariable Long orderId,
            @RequestParam boolean accepted,
            @RequestBody TransporterAnswerRequest request,
            HttpServletRequest http
    ) throws FirebaseMessagingException, ParseException {
        log.info("| userId: {} | orderId: {} | accepted : {}", userId, orderId, accepted); // TODO : BORRA ESTO :D
        Long userIdFromToken = jwtService.extractUserId(http);
        if (Objects.equals(userIdFromToken, userId)) {
            Transporter transporter = transporterService.getById(userId);
            assignmentManager.notify(request, transporter);
            if (accepted) {
                return ResponseEntity.ok("Se ha notificado al comprador y al productor");
            } else {
                return ResponseEntity.ok("Haz rechazado el pedido con ID: " + orderId);
            }
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Not allowed, expected: " + userIdFromToken + " passed: " + userId));
        }
    }

    /**
     * http://localhost:8095/v1/api/transporters/{userId}?available=true
     */
    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('TRANSPORTER')")
    public ResponseEntity<?> changeAvailabilityStatus(@PathVariable Long userId, @RequestParam boolean available, HttpServletRequest http) {
        Long userIdFromToken = jwtService.extractUserId(http);

        if (Objects.equals(userIdFromToken, userId)) {
            Transporter transporter = transporterService.getById(userId);
            transporter.setAvailable(available);
            transporterService.saveTransporter(transporter);

            return ResponseEntity.ok(new MessageResponse("Availability status changed to: " + transporter.isAvailable()));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Not allowed, expected: " + userIdFromToken + " passed: " + userId));
        }
    }
}
