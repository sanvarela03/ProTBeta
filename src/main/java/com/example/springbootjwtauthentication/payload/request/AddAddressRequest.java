package com.example.springbootjwtauthentication.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddAddressRequest {
    @NotBlank
    private String country;
    @NotBlank
    private String state;
    @NotBlank
    private String city;
    @NotBlank
    private String street;
    @NotBlank
    private String zip;
}
