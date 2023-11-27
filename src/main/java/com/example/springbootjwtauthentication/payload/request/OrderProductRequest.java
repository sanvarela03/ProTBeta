package com.example.springbootjwtauthentication.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderProductRequest {
    @NotBlank
    private Long productId;
    @NotBlank
    private int units;
}
