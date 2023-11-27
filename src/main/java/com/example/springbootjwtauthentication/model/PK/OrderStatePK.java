package com.example.springbootjwtauthentication.model.PK;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderStatePK implements Serializable {

    private static final long serialVersionUID = 321321123121L;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private Long stateId;
}
