package com.example.springbootjwtauthentication.payload.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransporterAnswerRequest {
    private Long orderId;
    private boolean accepted;
    private String estimatedPickupDate; // format : dd-MM-yyyy
    private String estimatedDeliveryDate;
}
