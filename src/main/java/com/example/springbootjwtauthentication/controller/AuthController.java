package com.example.springbootjwtauthentication.controller;

import com.example.springbootjwtauthentication.service.api.auth.PasswordForgotJwt;
import com.example.springbootjwtauthentication.payload.request.*;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.service.interfaces.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/api/auth")
public class AuthController {

    @Autowired
    private SignUp signUp;

    @Autowired
    private SignIn signIn;

    @Autowired
    private IRefreshToken refreshToken;

    @Autowired
    private SignOut signOut;

    @Autowired
    private ResetPassword resetPassword;

    @Autowired
    private PasswordForgotJwt passwordForgotJwt;

    /**
     * http://localhost:8095/api/auth/signup
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return signUp.doSignUp(signUpRequest);
    }

    /**
     * http://localhost:8095/api/auth/signin
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return signIn.doSignIn(loginRequest);
    }

    /**
     * http://localhost:8095/api/auth/refreshtoken
     */
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        return refreshToken.doRefreshToken(request);
    }

    /**
     * http://localhost:8095/api/auth/signout
     */
    @SecurityRequirement(name ="jwt-auth")
    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        return signOut.doSignOut();
    }


    /**
     * http://localhost:8095/api/auth/resetpassword
     */
    @PutMapping("/resetpassword")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPassRequest request) {
        return resetPassword.doReset(request);
    }

    /**
     * http://localhost:8095/api/auth/send-code
     */
    @PostMapping("/send-code")
    public ResponseEntity<MessageResponse> sendVerificationCodeByEmail(@Valid @RequestBody ForgotPasswordRequest request) {
        return passwordForgotJwt.sendVerificationCode(request.getEmail());
    }

    /**
     * http://localhost:8095/api/auth/verify-code
     */
    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@Valid @RequestBody VerificationCodeRequest request) {
        return passwordForgotJwt.isVerificationCodeValid(request.getEmail(), request.getVerificationCode());
    }

    /**
     * http://localhost:8095/api/auth/forgot-password
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ResetPassVerifiedCodeRequest request) {
        return passwordForgotJwt.resetPassword(request.getEmail(), request.getVerificationCode(), request.getNewPassword());
    }

}
