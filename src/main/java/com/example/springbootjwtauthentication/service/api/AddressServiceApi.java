package com.example.springbootjwtauthentication.service.api;

import com.example.springbootjwtauthentication.model.Address;
import com.example.springbootjwtauthentication.model.City;
import com.example.springbootjwtauthentication.model.User;
import com.example.springbootjwtauthentication.payload.request.AddAddressRequest;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.repository.AddressRepository;
import com.example.springbootjwtauthentication.repository.UserRepository;
import com.example.springbootjwtauthentication.security.jwt.JwtUtils;
import com.example.springbootjwtauthentication.service.implementations.*;
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
    @Autowired
    private CityService cityService;

    public ResponseEntity<MessageResponse> addAddress(HttpServletRequest http, AddAddressRequest request) {
        User user = userService.getUserByUsername(jwtService.extractUsername(http));
        Address newAddress = serializerService.getAddress(request, user);

        String countryName = request.getCountry().trim().toLowerCase();
        String stateName = request.getState().trim().toLowerCase();
        String cityName = request.getCity().trim().toLowerCase();

        boolean allExist = cityService.existsFiltered(countryName, stateName, cityName);
        boolean countryAndStateExist = cityService.existsFiltered(countryName, stateName);
        boolean onlyCountryExist = cityService.existsFiltered(countryName);

        if (allExist) {
            City city = cityService.getCityFiltered(countryName, stateName, cityName);
            newAddress.setCity(city);
        } else if (countryAndStateExist) {
            City city = cityService.createCity(countryName, stateName, cityName);
            newAddress.setCity(city);
        } else if (onlyCountryExist) {
            City city = cityService.createCityAndState(countryName, stateName, cityName);
            newAddress.setCity(city);
        } else {
            City city = cityService.createCityAndStateAndCountry(countryName, stateName, cityName);
            newAddress.setCity(city);
        }

        addressService.saveAddress(newAddress);

        if (request.getIsCurrentAddress()) {
            user.setCurrentAddress(newAddress);
            userService.saveUser(user);
        }
        return ResponseEntity.ok(new MessageResponse("Nueva dirección agregada correctamente"));
    }
}
