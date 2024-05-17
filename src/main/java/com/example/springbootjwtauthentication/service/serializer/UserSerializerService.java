package com.example.springbootjwtauthentication.service.serializer;

import com.example.springbootjwtauthentication.model.Enum.ERole;
import com.example.springbootjwtauthentication.model.Role;
import com.example.springbootjwtauthentication.model.User;
import com.example.springbootjwtauthentication.payload.request.SignupRequest;
import com.example.springbootjwtauthentication.service.implementations.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserSerializerService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleService roleService;

    public User getUser(SignupRequest request) {
        return User.builder()
                .name(request.getName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .email(request.getEmail())
                .phone(request.getPhone())
                .firebaseToken(request.getFirebaseToken())
                .password(encoder.encode(request.getPassword()))
                .build();
    }

    public Set<Role> getRoles(SignupRequest request) {
        Set<String> strRoles = request.getRole();
        Set<Role> roleEntities = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleService.getRoleByName(ERole.ROLE_USER);
            roleEntities.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleService.getRoleByName(ERole.ROLE_ADMIN);
                        roleEntities.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleService.getRoleByName(ERole.ROLE_MODERATOR);
                        roleEntities.add(modRole);

                        break;
                    case "prod":
                        Role prodRole = roleService.getRoleByName(ERole.ROLE_PRODUCER);
                        roleEntities.add(prodRole);

                        break;
                    case "cust":
                        Role customerRole = roleService.getRoleByName(ERole.ROLE_CUSTOMER);
                        roleEntities.add(customerRole);

                        break;
                    case "tr":
                        Role transporterRole = roleService.getRoleByName(ERole.ROLE_TRANSPORTER);
                        roleEntities.add(transporterRole);
                        break;
                    default:
                        Role userRole = roleService.getRoleByName(ERole.ROLE_USER);
                        roleEntities.add(userRole);
                }
            });
        }
        return roleEntities;
    }
}
