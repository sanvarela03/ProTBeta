package com.example.springbootjwtauthentication.model.PK;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Builder
public class OrderProductPK implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orderId;
    private Long productId;
}
