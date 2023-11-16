package com.example.springbootjwtauthentication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(
            name = "product_id"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "producer_id"
    )
    private Producer owner;
}
