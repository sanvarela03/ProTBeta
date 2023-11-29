package com.example.springbootjwtauthentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.*;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "customer_id",
            referencedColumnName = "id"
    )
    private Customer customer;

    @ManyToOne
    @JoinColumn(
            name = "producer_id",
            referencedColumnName = "id"
    )
    private Producer producer;

    @ManyToOne
    @JoinColumn(
            name = "transporter_id",
            referencedColumnName = "id"
    )
    private Transporter transporter;

    private Date estimatedDeliveryDate;

    private double orderCost;

    private double shippingCost;

    public Order(Customer customer, Producer producer) {
        this.customer = customer;
        this.producer = producer;
    }
}
