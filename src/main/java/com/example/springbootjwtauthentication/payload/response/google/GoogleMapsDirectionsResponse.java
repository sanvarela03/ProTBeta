package com.example.springbootjwtauthentication.payload.response.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class GoogleMapsDirectionsResponse {
    private String status;
    private List<Route> routes;
}

