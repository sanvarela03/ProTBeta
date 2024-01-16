package com.example.springbootjwtauthentication.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EasyDistanceRequest {
    private String origen;
    private String destino;
}
