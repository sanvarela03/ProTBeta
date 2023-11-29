package com.example.springbootjwtauthentication.service.api.auth;

import com.example.springbootjwtauthentication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class VerificationCodeService {
    @Autowired
    private PasswordEncoder encoder;

    public boolean isVerificationCodeExpired(User user) {
        long expirationTimeInMilliseconds = 15 * 60 * 1000; // 15 minutos de expiración
        long currentTime = new Date().getTime();
        long codeTime = user.getVerificationCodeTimestamp().getTime();

        return (currentTime - codeTime) > expirationTimeInMilliseconds;
    }

    public boolean verificationCodeMatches(User user, String verificationCode) {
        return encoder.matches(verificationCode, user.getVerificationCode());
    }

    public String generateVerificationCode() {
        Random random = new Random();

        int code = 100_000 + random.nextInt(900_000);

        return String.valueOf(code);
    }
}
