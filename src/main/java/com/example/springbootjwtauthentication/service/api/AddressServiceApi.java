package com.example.springbootjwtauthentication.service.api;

import com.example.springbootjwtauthentication.model.Address;
import com.example.springbootjwtauthentication.model.City;
import com.example.springbootjwtauthentication.model.User;
import com.example.springbootjwtauthentication.payload.request.AddAddressRequest;
import com.example.springbootjwtauthentication.payload.request.UpdateAddressRequest;
import com.example.springbootjwtauthentication.payload.response.AddressResponse;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.service.implementations.AddressService;
import com.example.springbootjwtauthentication.service.implementations.CityService;
import com.example.springbootjwtauthentication.service.implementations.JWTService;
import com.example.springbootjwtauthentication.service.implementations.UserService;
import com.example.springbootjwtauthentication.service.serializer.AddressSerializerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public ResponseEntity<List<AddressResponse>> getAllAddress(Long userId, HttpServletRequest httpServletRequest) {
        List<AddressResponse> addressResponseList = new ArrayList<>();

        addressService.getAllAddressByUserId(userId).forEach(
                address -> {
                    addressResponseList.add(address.toAddressResponse());
                }
        );

        return ResponseEntity.ok(addressResponseList);
    }

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

    public ResponseEntity<MessageResponse> updateAddress(Long userId, Long addressId, UpdateAddressRequest request, HttpServletRequest http) {
        Address address = addressService.getAddressById(addressId);

        address.update(request);

        User user = userService.getUserById(userId);
        if (request.getIsCurrentAddress()) {
            user.setCurrentAddress(address);
            userService.saveUser(user);
        }
        if (user.getCurrentAddress() != null) {
            if (user.getCurrentAddress().getId().equals(addressId) && !request.getIsCurrentAddress()) {
                user.setCurrentAddress(null);
                userService.saveUser(user);
            }
        }

        addressService.saveAddress(address);

        return ResponseEntity.ok(new MessageResponse("Dirección actualizada correctamente"));
    }

    public ResponseEntity<MessageResponse> deleteAddress(Long userId, Long id, HttpServletRequest http) {
        User user = userService.getUserById(userId);
        if (user.getCurrentAddress() != null) {
            if (user.getCurrentAddress().getId().equals(id)) {
                user.setCurrentAddress(null);
                userService.saveUser(user);
            }
        }
        addressService.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Dirección borrada correctamente"));
    }
}
