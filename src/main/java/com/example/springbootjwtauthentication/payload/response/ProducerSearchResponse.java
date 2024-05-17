package com.example.springbootjwtauthentication.payload.response;

import com.example.springbootjwtauthentication.model.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProducerSearchResponse {
    private Long producerId;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private AddressResponse currentAddress;
}
