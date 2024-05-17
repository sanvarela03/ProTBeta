package com.example.springbootjwtauthentication.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDetailResponse {
    private Long productId;
    private String name;
    private String description;
    private int unitsAvailable;
    private double price;
    private double weight;
    private double length;
    private double width;
    private double height;
}
