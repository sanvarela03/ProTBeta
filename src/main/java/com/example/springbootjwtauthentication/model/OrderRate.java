package com.example.springbootjwtauthentication.model;

import com.example.springbootjwtauthentication.model.PK.OrderRatePK;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRate implements Serializable {

    @EmbeddedId
    private OrderRatePK id = new OrderRatePK();


    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @MapsId("orderId")
    private Order order;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @MapsId("rateId")
    private Rate rate;

    private double total;

    private static final long serialVersionUID = 1L;
}
