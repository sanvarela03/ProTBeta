package com.example.springbootjwtauthentication.model.PK;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRatePK implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private Long rateId;
}
