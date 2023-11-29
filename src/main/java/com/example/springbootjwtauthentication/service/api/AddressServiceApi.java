package com.example.springbootjwtauthentication.service.api;

import com.example.springbootjwtauthentication.model.Address;
import com.example.springbootjwtauthentication.model.User;
import com.example.springbootjwtauthentication.payload.request.AddAddressRequest;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.repository.AddressRepository;
import com.example.springbootjwtauthentication.repository.UserRepository;
import com.example.springbootjwtauthentication.security.jwt.JwtUtils;
import com.example.springbootjwtauthentication.service.implementations.AddressService;
import com.example.springbootjwtauthentication.service.implementations.JWTService;
import com.example.springbootjwtauthentication.service.implementations.UserService;
import com.example.springbootjwtauthentication.service.serializer.AddressSerializerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceApi {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressSerializerService serializerService;
    @Autowired
    private AddressService addressService;

    public ResponseEntity<MessageResponse> addAddress(HttpServletRequest http, AddAddressRequest request) {
        User user = userService.getUserByUsername(jwtService.extractUsername(http));
        Address newAddress = serializerService.getAddress(request, user);

        addressService.saveAddress(newAddress);

        if (request.getIsCurrentAddress()) {
            user.setCurrentAddress(newAddress);
            userService.saveUser(user);
        }
        return ResponseEntity.ok(new MessageResponse("Nueva dirección agregada correctamente"));
    }
}
