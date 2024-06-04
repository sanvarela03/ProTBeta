package com.example.springbootjwtauthentication.model;

import com.example.springbootjwtauthentication.payload.response.AddressResponse;
import com.example.springbootjwtauthentication.payload.response.ProducerInfoResponse;
import com.example.springbootjwtauthentication.payload.response.ProducerSearchResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "producers"
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Producer extends User {
    private String fechaDeRegistro;

    public ProducerSearchResponse toProducerSearchResponse() {
        return ProducerSearchResponse.builder()
                .producerId(this.getId())
                .name(this.getName())
                .lastName(this.getLastName())
                .email(this.getEmail())
                .phone(this.getPhone())
                .currentAddress(this.getCurrentAddress().toAddressResponse())
                .build();
    }

    public ProducerInfoResponse toProducerInfoResponse(List<AddressResponse> addressResponseList, List<Product> products) {
        ProducerInfoResponse producerInfoResponse = new ProducerInfoResponse();


        producerInfoResponse.setProducerId(this.getId());
        producerInfoResponse.setName(this.getName());
        producerInfoResponse.setLastname(this.getLastName());
        producerInfoResponse.setUsername(this.getUsername());
        producerInfoResponse.setEmail(this.getEmail());
        producerInfoResponse.setPhone(this.getPhone());
        if (this.getCurrentAddress() != null) {
            producerInfoResponse.setCurrentAddressId(this.getCurrentAddress().getId());
        }
        producerInfoResponse.setAddressList(addressResponseList);
        producerInfoResponse.setProductsList(products);

        return producerInfoResponse;
    }
}
