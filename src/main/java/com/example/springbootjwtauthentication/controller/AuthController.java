package com.example.springbootjwtauthentication.controller;

import com.example.springbootjwtauthentication.controller.service.implementations.PasswordForgotJwt;
import com.example.springbootjwtauthentication.controller.service.interfaces.*;
import com.example.springbootjwtauthentication.payload.request.*;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    SignUp signUp;

    @Autowired
    SignIn signIn;

    @Autowired
    IRefreshToken refreshToken;

    @Autowired
    SignOut signOut;

    @Autowired
    ResetPassword resetPassword;

    @Autowired
    PasswordForgotJwt passwordForgotJwt;

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
    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        return signOut.doSignOut();
    }


    /**
     * http://localhost:8095/api/auth/resetpassword
     */
    @PutMapping("/resetpassword")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPassRequest request) {

//        logger.info("request: {}", request.toString());
//
//        User userTest = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
//        logger.info("password: {}", userTest.getPassword());
//        logger.info("requestOldPasswordEncoded: {}", encoder.encode(request.getOldPassword()));
//
//        if (userRepository.existsByUsernameAndPassword(request.getUsername(), request.getOldPassword())) {
//            return ResponseEntity.badRequest().body(new MessageResponse("S_Error: Username and Old password not found for refresh !!"));
//        }
//
//        User user = userRepository.findByUsernameAndPassword(request.getUsername(), encoder.encode(request.getOldPassword())).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + request.getUsername() + " And Password: " + request.getOldPassword()));
//
//        logger.info("user: {}", user);
//
//        return ResponseEntity.ok(new MessageResponse("Usuario y contrasenia encontrados"));

        return resetPassword.doReset(request);
    }

    @PostMapping("/send-code")
    public ResponseEntity<MessageResponse> sendVerificationCodeByEmail(@Valid @RequestBody ForgotPasswordRequest request) {
        return passwordForgotJwt.sendVerificationCode(request.getEmail());
    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@Valid @RequestBody VerificationCodeRequest request) {
        return passwordForgotJwt.isVerificationCodeValid(request.getEmail(), request.getVerificationCode());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ResetPassVerifiedCodeRequest request) {
        return passwordForgotJwt.resetPassword(request.getEmail(), request.getVerificationCode(), request.getNewPassword());
    }

}
