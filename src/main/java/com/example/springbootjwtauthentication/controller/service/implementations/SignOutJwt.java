package com.example.springbootjwtauthentication.controller.service.implementations;

import com.example.springbootjwtauthentication.controller.service.interfaces.SignOut;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.security.service.RefreshTokenService;
import com.example.springbootjwtauthentication.security.service.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SignOutJwt implements SignOut {

    private static final Logger logger = LoggerFactory.getLogger(SignOutJwt.class);

    @Autowired
    RefreshTokenService refreshTokenService;

    @Override
    public ResponseEntity<MessageResponse> doSignOut() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        logger.info("S_USER_DETAILS: {}", userDetails);

        Long userId = userDetails.getId();
        refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }
}
