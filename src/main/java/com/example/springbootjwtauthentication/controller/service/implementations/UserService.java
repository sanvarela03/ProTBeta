package com.example.springbootjwtauthentication.controller.service.implementations;

import com.example.springbootjwtauthentication.model.Address;
import com.example.springbootjwtauthentication.model.User;
import com.example.springbootjwtauthentication.payload.request.AddAddressRequest;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.repository.AddressRepository;
import com.example.springbootjwtauthentication.repository.UserRepository;
import com.example.springbootjwtauthentication.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public ResponseEntity<MessageResponse> addAddress(
            HttpServletRequest http,
            AddAddressRequest request
    ) {
        User user = findUser(http);
        Address newAddress = createNewAddress(request, user);
        addressRepository.save(newAddress);

        if (request.getIsCurrentAddress()) {
            user.setCurrentAddress(newAddress);
            userRepository.save(user);
        }

        return ResponseEntity.ok(new MessageResponse("Nueva dirección agregada correctamente"));
    }

    private User findUser(HttpServletRequest http) {
        String username = jwtUtils.getUserNameFromJwtToken(http);
        User user = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Nombre de usuario : " + username + " no encontrado.")
                );
        return user;
    }

    private Address createNewAddress(AddAddressRequest request, User user) {
        return Address.builder()
                .user(user)
                .country(request.getCountry())
                .state(request.getState())
                .city(request.getCity())
                .street(request.getStreet())
                .zip(request.getZip())
                .build();
    }
}
