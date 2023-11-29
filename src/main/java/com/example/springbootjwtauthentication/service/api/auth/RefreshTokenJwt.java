package com.example.springbootjwtauthentication.service.api.auth;

import com.example.springbootjwtauthentication.service.interfaces.IRefreshToken;
import com.example.springbootjwtauthentication.exception.TokenRefreshException;
import com.example.springbootjwtauthentication.payload.request.TokenRefreshRequest;
import com.example.springbootjwtauthentication.payload.response.TokenRefreshResponse;
import com.example.springbootjwtauthentication.security.jwt.JwtUtils;
import com.example.springbootjwtauthentication.security.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenJwt implements IRefreshToken {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public ResponseEntity<TokenRefreshResponse> doRefreshToken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(com.example.springbootjwtauthentication.model.RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }
}
