package com.example.springbootjwtauthentication.payload.response.bing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BingMapsDirectionsResponse {
    private String authenticationResultCode;
    private List<ResourceSet> resourceSets;
    private double statusCode;
    private String statusDescription;
    private String traceId;
}

