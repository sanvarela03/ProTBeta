package com.example.springbootjwtauthentication.service.serializer;

import com.example.springbootjwtauthentication.model.Producer;
import com.example.springbootjwtauthentication.model.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProducerSerializerService {
    public Producer getProducerFromUser(User user) {
        return Producer.builder()
                .name(user.getName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .fechaDeRegistro((new Date()).toString())//TODO : Complementar
                .email(user.getEmail())
                .phone(user.getPhone())
                .firebaseToken(user.getFirebaseToken())
                .password(user.getPassword())
                .roleEntities(user.getRoleEntities())
                .build();
    }
}
