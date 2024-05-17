package com.example.springbootjwtauthentication.model;

import com.example.springbootjwtauthentication.model.PK.OrderStatusPK;

import com.example.springbootjwtauthentication.payload.response.StatusResponse;
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
    private OrderStatusPK id = new OrderStatusPK();

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @MapsId("orderId")
    private Order order;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @MapsId("statusId")
    private Status status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

//    @PrePersist
//    public void prePersist() {
//        createdAt = new Date();
//    }

    public OrderStatus(Order order, Status status) {
        this.order = order;
        this.status = status;
    }

    public StatusResponse toStatusResponse() {
        return StatusResponse.builder()
                .statusId(this.getStatus().getId())
                .name(this.getStatus().getName().toString())
                .createdAt(this.getCreatedAt())
                .orderId(this.getOrder().getId())
                .build();
    }
}
