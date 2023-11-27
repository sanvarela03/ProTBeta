package com.example.springbootjwtauthentication.model;


import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "product_images")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
