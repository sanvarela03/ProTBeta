package com.example.springbootjwtauthentication.payload.response;

import com.example.springbootjwtauthentication.model.Product;
import com.example.springbootjwtauthentication.payload.OrderInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfoResponse {
    private Long customerId;
    private String username;
    private String name;
    private String lastname;
    private String email;
    private Long currentAddressId;
    private List<AddressResponse> addressList;
    private List<OrderInfoResponse> orderInfoResponseList;
}
