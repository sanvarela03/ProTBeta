package com.example.springbootjwtauthentication.payload.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private double price;
    @NotBlank
    private String currency;
    @NotBlank
    private double weightPerUnit_kg;
    @NotBlank
    private double length_cm;
    @NotBlank
    private double width_cm;
    @NotBlank
    private double height_cm;
    @NotBlank
    private int unitsAvailable;
}
