package com.example.springbootjwtauthentication.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateAddressRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String street;
    @NotBlank
    private String instruction;
    @NotBlank
    private Boolean isCurrentAddress;
    @NotBlank
    private Double latitude;
    @NotBlank
    private Double longitude;
}

