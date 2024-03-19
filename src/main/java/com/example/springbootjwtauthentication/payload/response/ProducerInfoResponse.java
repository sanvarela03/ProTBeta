package com.example.springbootjwtauthentication.payload.response;

import com.example.springbootjwtauthentication.model.Address;
import com.example.springbootjwtauthentication.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProducerInfoResponse {
    private Long producerId;
    private String username;
    private String name;
    private String lastname;
    private String email;
    private List<Address> addressList;
    private List<Product> productsList;
}
