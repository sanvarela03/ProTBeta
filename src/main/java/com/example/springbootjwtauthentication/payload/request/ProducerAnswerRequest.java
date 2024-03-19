package com.example.springbootjwtauthentication.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProducerAnswerRequest {
    private Long orderId;
    private boolean accepted;
    private String message;
}
