package com.example.springbootjwtauthentication.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoRequest {
    private String username;
    private String name;
    private String lastName;
    private String email;
    private String phone;
}
