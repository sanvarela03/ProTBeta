package com.example.springbootjwtauthentication.service.serializer;

import com.example.springbootjwtauthentication.model.Customer;
import com.example.springbootjwtauthentication.model.User;
import org.springframework.stereotype.Service;

@Service
public class CustomerSerializerService {
    public Customer getCustomerFromUser(User user) {
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
