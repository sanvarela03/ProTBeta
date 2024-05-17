package com.example.springbootjwtauthentication.controller;

import com.example.springbootjwtauthentication.model.Transporter;
import com.example.springbootjwtauthentication.payload.request.TransporterAnswerRequest;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.service.api.TransporterServiceApi;
import com.example.springbootjwtauthentication.service.implementations.JWTService;
import com.example.springbootjwtauthentication.service.implementations.TransporterAssignmentManager;
import com.example.springbootjwtauthentication.service.implementations.TransporterService;
import com.google.firebase.messaging.FirebaseMessagingException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name ="jwt-auth")
public class TransporterController {
    @Autowired
    private TransporterAssignmentManager assignmentManager;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private TransporterService transporterService;
    @Autowired
    private TransporterServiceApi transporterServiceApi;

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('TRANSPORTER')")
    public ResponseEntity<?> getTransporterInfo(@PathVariable Long userId) {
        return transporterServiceApi.getTransporter(userId);
    }


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

    @PutMapping("/{transporterId}/orders/{orderId}/picked-up")
    @PreAuthorize("hasRole('TRANSPORTER')")
    public ResponseEntity<?> confirmPickup(
            @PathVariable Long transporterId,
            @PathVariable Long orderId
    ) {
        return transporterServiceApi.confirmPickup(orderId);
    }


    @PutMapping("/{transporterId}/orders/{orderId}/delivered")
    @PreAuthorize("hasRole('TRANSPORTER')")
    public ResponseEntity<?> confirmDelivery(
            @PathVariable Long transporterId,
            @PathVariable Long orderId,
            @RequestParam String code
    ) {
        return transporterServiceApi.confirmDelivery(orderId, code);
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
