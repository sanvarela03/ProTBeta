package com.example.springbootjwtauthentication.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
    private Long id;
    private String username;
    private String email;
    private String token;
    private String refreshToken;
    private String type = "Bearer";
    private List<String> roles;
}
