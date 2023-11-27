package com.example.springbootjwtauthentication.model;

import com.example.springbootjwtauthentication.model.PK.OrderProductPK;
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
public class OrderProduct implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private OrderProductPK id = new OrderProductPK();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    private Product product;
    private int units;

    public OrderProduct(Order order, Product product, int units) {
        this.order = order;
        this.product = product;
        this.units = units;
    }
}
