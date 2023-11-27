package com.example.springbootjwtauthentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String country;
    private String state;
    private String city;
    private String street;
    private String zip;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user;
}
