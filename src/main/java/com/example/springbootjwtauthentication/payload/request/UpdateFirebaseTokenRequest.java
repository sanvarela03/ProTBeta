package com.example.springbootjwtauthentication.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFirebaseTokenRequest {
    private String firebaseToken;
}
