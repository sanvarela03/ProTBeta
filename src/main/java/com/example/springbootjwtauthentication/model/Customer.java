package com.example.springbootjwtauthentication.model;

import com.example.springbootjwtauthentication.payload.OrderInfoResponse;
import com.example.springbootjwtauthentication.payload.response.AddressResponse;
import com.example.springbootjwtauthentication.payload.response.CustomerInfoResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(
        name = "customers"
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Customer extends User {

    private double balance;




    public CustomerInfoResponse toCustomerInfoResponse(
            List<AddressResponse> addressResponseList,
            List<OrderInfoResponse> orderInfoResponseList
    ) {
        CustomerInfoResponse customerInfoResponse = new CustomerInfoResponse();
        customerInfoResponse.setCustomerId(this.getId());
        customerInfoResponse.setUsername(this.getUsername());
        customerInfoResponse.setName(this.getName());
        customerInfoResponse.setLastname(this.getLastName());
        customerInfoResponse.setEmail(this.getEmail());

        if (this.getCurrentAddress() != null) {
            customerInfoResponse.setCurrentAddressId(this.getCurrentAddress().getId());
        }
        customerInfoResponse.setAddressList(addressResponseList);
        customerInfoResponse.setOrderInfoResponseList(orderInfoResponseList);

        return customerInfoResponse;
    }
}
