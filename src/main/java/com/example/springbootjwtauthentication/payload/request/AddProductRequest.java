package com.example.springbootjwtauthentication.payload.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductRequest {
    private String name;
    private String description;
    private double price;
    private int unitsAvailable;
}
