package com.example.springbootjwtauthentication.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String street;
    private String instruction;
    private double latitude;
    private double longitude;

    @ManyToOne
    @JoinColumn(
            name = "city_id",
            referencedColumnName = "id"
    )
    @ToString.Exclude
    private City city;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    @ToString.Exclude
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("userId")
    private User user;

    @Serial
    private static final long serialVersionUID = 1L;
}
