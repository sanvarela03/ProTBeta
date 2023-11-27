package com.example.springbootjwtauthentication.model;

import com.example.springbootjwtauthentication.model.PK.OrderStatePK;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderState implements Serializable {
    private static final long serialVersionUID = 321321123121L;

    @EmbeddedId
    private OrderStatePK id = new OrderStatePK();

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
    @MapsId("stateId")
    private State state;

    private Date createdAt;

    public OrderState(Order order, State state) {
        this.order = order;
        this.state = state;
        createdAt = new Date();
    }
}
