package com.example.springbootjwtauthentication.controller.service.implementations;

import com.example.springbootjwtauthentication.controller.service.interfaces.SignUp;
import com.example.springbootjwtauthentication.model.Customer;
import com.example.springbootjwtauthentication.model.Enum.ERole;
import com.example.springbootjwtauthentication.model.Producer;
import com.example.springbootjwtauthentication.model.Role;
import com.example.springbootjwtauthentication.model.User;
import com.example.springbootjwtauthentication.payload.request.SignupRequest;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.repository.CustomerRepository;
import com.example.springbootjwtauthentication.repository.ProducerRepository;
import com.example.springbootjwtauthentication.repository.RoleRepository;
import com.example.springbootjwtauthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class SignUpJwt implements SignUp {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public ResponseEntity<MessageResponse> doSignUp(SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("S_Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("S_Error: Email is already in use!"));
        }

        User user = createNewUserAccount(request);
        user.setRoleEntities(getRoles(request));


        if (request.getRole().contains("prod")) {
            producerRepository.save(createProducerFromUser(user));

        } else if (request.getRole().contains("cust")) {
            customerRepository.save(createCustomerFromUser(user));
        } else {
            userRepository.save(user);
        }

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    private Set<Role> getRoles(SignupRequest request) {
        Set<String> strRoles = request.getRole();
        Set<Role> roleEntities = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roleEntities.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roleEntities.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roleEntities.add(modRole);

                        break;
                    case "prod":
                        Role prodRole = roleRepository.findByName(ERole.ROLE_PRODUCER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roleEntities.add(prodRole);

                        break;
                    case "cust":
                        Role customerRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roleEntities.add(customerRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roleEntities.add(userRole);
                }
            });
        }
        return roleEntities;
    }

    private User createNewUserAccount(SignupRequest request) {
        return User.builder()
                .name(request.getName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .build();
    }

    private Producer createProducerFromUser(User user) {
        return Producer.builder()
                .name(user.getName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .fechaDeRegistro((new Date()).toString())//TODO : Complementar
                .email(user.getEmail())
                .password(user.getPassword())
                .roleEntities(user.getRoleEntities())
                .build();
    }

    private Customer createCustomerFromUser(User user) {
        return Customer.builder()
                .name(user.getName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .roleEntities(user.getRoleEntities())
                .build();
    }
}
