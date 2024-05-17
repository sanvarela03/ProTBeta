package com.example.springbootjwtauthentication.model;

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
}
