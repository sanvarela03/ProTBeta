package com.example.springbootjwtauthentication.service.api.auth;

import com.example.springbootjwtauthentication.service.implementations.*;
import com.example.springbootjwtauthentication.service.interfaces.SignUp;
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
import com.example.springbootjwtauthentication.service.serializer.CustomerSerializerService;
import com.example.springbootjwtauthentication.service.serializer.ProducerSerializerService;
import com.example.springbootjwtauthentication.service.serializer.TransporterSerializerService;
import com.example.springbootjwtauthentication.service.serializer.UserSerializerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class SignUpJwt implements SignUp {
    @Autowired
    private UserService userService;
    @Autowired
    private ProducerService producerService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TransporterService transporterService;

    @Autowired
    private UserSerializerService userSerializerService;
    @Autowired
    private ProducerSerializerService producerSerializerService;
    @Autowired
    private CustomerSerializerService customerSerializerService;
    @Autowired
    private TransporterSerializerService transporterSerializerService;

    @Override
    public ResponseEntity<MessageResponse> doSignUp(SignupRequest request) {
        log.info("SignUpJwt | doSignUp | SignupRequest = {}", request);
        if (userService.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("S_Error: Este usuario ya está en uso"));
        }
        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("S_Error: Este correo ya está en uso"));
        }

        User user = userSerializerService.getUser(request);
        user.setRoleEntities(userSerializerService.getRoles(request));

        log.info("SignUpJwt | doSignUp | user = {}", user);

        if (request.getRole().contains("prod")) {
            producerService.saveProducer(user.toProducer());
        } else if (request.getRole().contains("cust")) {
            customerService.saveCustomer(user.toCustomer());
        } else if (request.getRole().contains("tr")) {
            transporterService.saveTransporter(user.toTransporter());
        } else {
            userService.saveUser(user);
        }

        return ResponseEntity.ok(new MessageResponse("Usuario registrado correctamente!!"));
    }
}
