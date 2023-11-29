package com.example.springbootjwtauthentication.service.api.auth;

import com.example.springbootjwtauthentication.model.User;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.payload.response.VerificationCodeResponse;
import com.example.springbootjwtauthentication.security.service.RefreshTokenService;
import com.example.springbootjwtauthentication.service.implementations.EmailService;
import com.example.springbootjwtauthentication.service.implementations.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class PasswordForgotJwt {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private VerificationCodeService verificationCodeService;

    public ResponseEntity<MessageResponse> sendVerificationCode(String email) {
        boolean exists = userService.existsByEmail(email);
        if (exists) {
            User user = userService.getUserByEmail(email);
            String verificationCode = verificationCodeService.generateVerificationCode();

            user.setVerificationCode(encoder.encode(verificationCode));
            user.setVerificationCodeTimestamp(new Date());

            userService.saveUser(user);

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
        User user = userService.getUserByEmail(email);

        boolean isExpired = verificationCodeService.isVerificationCodeExpired(user);
        boolean match = verificationCodeService.verificationCodeMatches(user, verificationCode);

        System.out.println("Expiro ? : = " + isExpired);

        if (match && !isExpired) {
            response.setIsValid(true);
            return ResponseEntity.ok(response);
        } else {
            response.setIsValid(false);
            return ResponseEntity.badRequest().body(response);
        }
    }

    public ResponseEntity<MessageResponse> resetPassword(String email, String verificationCode, String newPassword) {
        User user = userService.getUserByEmail(email);
        boolean isExpired = verificationCodeService.isVerificationCodeExpired(user);
        boolean match = verificationCodeService.verificationCodeMatches(user, verificationCode);

        if (!isExpired) {
            if (match) {
                userService.updatePasswordById(encoder.encode(newPassword), user.getId());
                userService.resetVerificationCodeById(user.getId());
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
