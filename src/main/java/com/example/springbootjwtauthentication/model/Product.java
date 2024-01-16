package com.example.springbootjwtauthentication.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
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
public class Product implements Serializable {

    private static final long serialVersionUID = 1879238L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double weightPerUnit;
    private double price;
    private int unitsAvailable;
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(
            name = "producer_id"
    )
    @ToString.Exclude
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("producerId")
    private Producer producer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @JsonIgnore
    @ToString.Exclude
    private List<ProductImage> images;

    public Product(String name, int unitsAvailable, String description, double price, boolean isAvailable, Producer producer) {
        this.name = name;
        this.unitsAvailable = unitsAvailable;
        this.description = description;
        this.price = price;
        this.isAvailable = isAvailable;
        this.producer = producer;
    }
}
