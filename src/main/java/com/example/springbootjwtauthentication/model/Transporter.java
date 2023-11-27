package com.example.springbootjwtauthentication.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "transporters"
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Transporter extends User {
    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL
    )
    private List<Vehicle> vehicles = new ArrayList<>();
}
