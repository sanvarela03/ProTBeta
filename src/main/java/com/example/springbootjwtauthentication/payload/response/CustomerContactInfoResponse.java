package com.example.springbootjwtauthentication.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerContactInfoResponse {
    private Long customerId;
    private String completeName;
    private String phone;
    private String email;
}
