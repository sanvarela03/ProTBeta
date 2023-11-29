package com.example.springbootjwtauthentication.service.api.auth;

import com.example.springbootjwtauthentication.service.interfaces.ResetPassword;
import com.example.springbootjwtauthentication.payload.request.ResetPassRequest;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.repository.UserRepository;
import com.example.springbootjwtauthentication.security.service.RefreshTokenService;
import com.example.springbootjwtauthentication.security.service.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordJwt implements ResetPassword {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;

    /**
     * 0. UPDATE HTTP REQUEST - PUT (OK)
     * 1. Revisar la solicitud - jwt - y obtener el usuario (OK)
     * 2. Verificar que la contraseña antigua coincide con la registrada en la base de datos (basicamente como hacer otro login)
     * 3. Si las contraseñas coinciden proceder con el siguiente paso de lo contrario lanzar un mensaje de error indicando que la contraseña no coincide
     * 4. Si las contraseña antigua coincide entonces establecer la nueva contraseña
     * 5. Buscar al usuario con el jwt en repositorio
     * 6. Codificar la contraseña nueva y pasarla al usuario
     * 7. Guardar el usuario en la base de datos
     * 8. Mostrar un mensaje de ok 200
     *
     * @param request la la carga util que tiene la contraseña antigua y nueva
     */
    @Override
    public ResponseEntity<MessageResponse> doReset(ResetPassRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        if (encoder.matches(request.getOldPassword(), userDetails.getPassword())) {
            SecurityContextHolder.clearContext();
            signOut(userDetails);
            userRepository
                    .updatePasswordById(encoder.encode(request.getNewPassword()), userDetails.getId().toString())
                    .orElseThrow(() -> new UsernameNotFoundException("S_ID : " + userDetails.getId() + " no encontrado"));
            return ResponseEntity.ok(new MessageResponse("Contraseña cambiada correctamente"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("La contraseña antigua no coincide."));
        }
    }

    private void signOut(UserDetailsImpl userDetails) {
        refreshTokenService.deleteByUserId(userDetails.getId());
        SecurityContextHolder.clearContext();
    }
}
