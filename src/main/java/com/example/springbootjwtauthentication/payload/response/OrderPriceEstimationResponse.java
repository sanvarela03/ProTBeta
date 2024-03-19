package com.example.springbootjwtauthentication.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPriceEstimationResponse {
    private String distance;
    private String lowerDistanceRate;
    private String higherDistanceRate;
    private String averageDistanceRate;
    private String time;
    private String timeRate;
    private String costPerKg;
}
