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
public class OrderStatus implements Serializable {
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
    @MapsId("statusId")
    private Status status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }

    public OrderStatus(Order order, Status status) {
        this.order = order;
        this.status = status;
    }
}
