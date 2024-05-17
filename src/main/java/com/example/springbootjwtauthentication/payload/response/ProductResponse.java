package com.example.springbootjwtauthentication.payload.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private Long productId;

    private String name;

    private String description;

    private Integer units;

    private double price;

    private Long orderId;
}
