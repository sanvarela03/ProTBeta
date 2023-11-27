package com.example.springbootjwtauthentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(
        name = "products"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    private int unitsAvailable;

    private String description;

    private double price;

    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(
            name = "producer_id"
    )
    @ToString.Exclude
    @JsonIgnore
    private Producer owner;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @JsonIgnore
    private List<ProductImage> images;
}
