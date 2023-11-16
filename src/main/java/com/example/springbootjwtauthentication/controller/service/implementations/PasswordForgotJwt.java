package com.example.springbootjwtauthentication.controller.service.implementations;

import com.example.springbootjwtauthentication.model.User;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.payload.response.VerificationCodeResponse;
import com.example.springbootjwtauthentication.repository.UserRepository;
import com.example.springbootjwtauthentication.security.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;


@Service
public class PasswordForgotJwt {
    @Autowired
    private UserRepository repository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    RefreshTokenService refreshTokenService;

    public ResponseEntity<MessageResponse> sendVerificationCode(String email) {
        if (repository.existsByEmail(email)) {
            User user = repository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario con email: " + email + " no encontrado"));

            String verificationCode = generateVerificationCode();

            user.setVerificationCode(encoder.encode(verificationCode));
            user.setVerificationCodeTimestamp(new Date());

            repository.save(user);

            String emailContent = "<html>" +
                    "<head>" +
                    "<style>" +
                    ".container { width: 60%; margin: 0 auto; text-align: center; border: 2px solid #ddd; border-radius: 10px; padding: 10px; }" +
                    ".code { font-weight: bold; font-size: 24px; }" +
                    ".codeA { font-weight: bold; font-size: 16px; }" +
                    "p { font-size: 16px; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class='container'>" +
                    "<p>Hola,<span class='codeA'>" + user.getName() + "</span></p>" +
                    "<p>Tu código de verificación es: </p>" +
                    "<div class='code'>" + verificationCode + "</div>" +
                    "<p>Utiliza este código para completar el proceso de verificación. No comparatas este código con nadie.</p>" +
                    "<p>Este código vence dentro de 15 minutos.</p>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            emailService.sendEmail(email, "Restablecimiento de contraseña", emailContent);

            return ResponseEntity.ok(new MessageResponse("Se ha enviado un código para restablecer la contraseña a tu dirección de correo electrónico"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("No se encontró ningún usuario con este correo electrónico: " + email));
        }

    }

    public ResponseEntity<VerificationCodeResponse> isVerificationCodeValid(String email, String verificationCode) {

        VerificationCodeResponse response = new VerificationCodeResponse(false);

        User user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró el usuario con email: " + email));

        System.out.println("Expiro ? : = " + isVerificationCodeExpired(user));

        if (encoder.matches(verificationCode, user.getVerificationCode()) && !isVerificationCodeExpired(user)) {
            response.setIsValid(true);
            return ResponseEntity.ok(response);
        } else {
            response.setIsValid(false);
            return ResponseEntity.badRequest().body(response);
        }
    }

    private boolean isVerificationCodeExpired(User user) {
        long expirationTimeInMilliseconds = 15 * 60 * 1000; // 15 minutos de expiración
        long currentTime = new Date().getTime();
        long codeTime = user.getVerificationCodeTimestamp().getTime();

        return (currentTime - codeTime) > expirationTimeInMilliseconds;
    }


    private boolean verificationCodeMatches(User user, String verificationCode) {
        return encoder.matches(verificationCode, user.getVerificationCode());
    }

    private String generateVerificationCode() {
        Random random = new Random();

        int code = 100_000 + random.nextInt(900_000);

        return String.valueOf(code);
    }

    public ResponseEntity<MessageResponse> resetPassword(
            String email,
            String verificationCode,
            String newPassword
    ) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró el usuario con email: " + email));

        if (!isVerificationCodeExpired(user)) {
            if (verificationCodeMatches(user, verificationCode)) {

                repository.updatePasswordById(encoder.encode(newPassword), user.getId().toString())
                        .orElseThrow(() -> new UsernameNotFoundException("No se pudo actualizar el usuario con id: " + user.getId()));
                repository.resetVerificationCodeById(user.getId())
                        .orElseThrow(() -> new UsernameNotFoundException("No se pudo reestablecer el codigo de verificacion con id usuario:" + user.getId()));

                refreshTokenService.deleteByUserId(user.getId());
                return ResponseEntity.ok(new MessageResponse("Su contraseña ha sido cambiada correctamente."));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("El código de verificación proporcionado no coincide."));
            }
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("El código de verificación expiró, por favor intentelo nuevamente."));
        }
    }

}
