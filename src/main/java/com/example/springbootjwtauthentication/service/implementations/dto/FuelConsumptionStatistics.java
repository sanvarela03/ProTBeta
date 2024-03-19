package com.example.springbootjwtauthentication.service.implementations.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FuelConsumptionStatistics {
    private double average;
    private  double standardDeviation;
}
