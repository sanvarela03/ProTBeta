package com.example.springbootjwtauthentication.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponse {
    private Long id;
    private String name;
    private String street;
    private String instruction;
    private double latitude;
    private double longitude;
    private String city;
    private String state;
    private String country;
    private Long userId;
}
