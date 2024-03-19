package com.example.springbootjwtauthentication.service.implementations.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GasCost {
    private double averageCost;
    private double higherCost;
    private double lowerCost;
    private double distance;
    private double time;
}
