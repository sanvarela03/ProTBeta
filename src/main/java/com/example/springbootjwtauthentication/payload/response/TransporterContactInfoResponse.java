package com.example.springbootjwtauthentication.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransporterContactInfoResponse {
    private Long transporterId;
    private String completeName;
    private String phone;
    private String email;
}
