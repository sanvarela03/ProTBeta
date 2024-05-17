package com.example.springbootjwtauthentication.model;

import com.example.springbootjwtauthentication.model.PK.OrderProductPK;
import com.example.springbootjwtauthentication.payload.response.ProductResponse;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

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
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("orderId")
    @ToString.Exclude
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @ToString.Exclude
    private Product product;

    private int units;

    public OrderProduct(Order order, Product product, int units) {
        this.order = order;
        this.product = product;
        this.units = units;
    }

    public ProductResponse toProductResponse() {
        return ProductResponse.builder()
                .productId(this.getProduct().getId())
                .name(this.getProduct().getName())
                .description(this.getProduct().getDescription())
                .units(this.getUnits())
                .price(this.getProduct().getPrice())
                .orderId(this.getOrder().getId())
                .build();
    }
}
