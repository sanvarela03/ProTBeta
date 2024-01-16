package com.example.springbootjwtauthentication.service.serializer;

import com.example.springbootjwtauthentication.model.Address;
import com.example.springbootjwtauthentication.model.User;
import com.example.springbootjwtauthentication.payload.request.AddAddressRequest;
import org.springframework.stereotype.Service;

@Service
public class AddressSerializerService {
    public Address getAddress(AddAddressRequest request, User user) {
        return Address.builder()
                .name(request.getName().trim().toUpperCase())
                .user(user)
                .country(request.getCountry())
                .state(request.getState())
                .city(request.getCity())
                .street(request.getStreet())
                .zip(request.getZip())
                .latitude(Double.parseDouble(request.getLatitude()))
                .longitude(Double.parseDouble(request.getLongitude()))
                .build();
    }
}
