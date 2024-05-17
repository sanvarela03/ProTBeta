package com.example.springbootjwtauthentication.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoResponse {
    private Long userId;
    private String username;
    private String name;
    private String lastName;
    private String email;
    private String phone;
}
