package com.example.springbootjwtauthentication.service.serializer;

import com.example.springbootjwtauthentication.model.Transporter;
import com.example.springbootjwtauthentication.model.User;
import org.springframework.stereotype.Service;

@Service
public class TransporterSerializerService {
    public Transporter getTransporterFromUser(User user) {
        return Transporter.builder()
                .name(user.getName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .roleEntities(user.getRoleEntities())
                .build();
    }
}
