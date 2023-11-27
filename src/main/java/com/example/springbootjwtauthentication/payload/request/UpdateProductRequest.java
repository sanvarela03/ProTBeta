package com.example.springbootjwtauthentication.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductRequest {
    @NotBlank
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private double price;
    @NotBlank
    private int unitsAvailable;
    @NotBlank
    private boolean available;
}
