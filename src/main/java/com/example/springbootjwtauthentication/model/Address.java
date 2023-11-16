package com.example.springbootjwtauthentication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String country;
    private String state;
    private String city;
    private String street;
    private String zip;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
