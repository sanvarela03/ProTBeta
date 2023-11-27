package com.example.springbootjwtauthentication.model;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Product> products = new ArrayList<>();
}
