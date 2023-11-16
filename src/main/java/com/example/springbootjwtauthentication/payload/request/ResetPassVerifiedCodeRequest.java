package com.example.springbootjwtauthentication.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPassVerifiedCodeRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String verificationCode;
    @NotBlank
    private String newPassword;
}
