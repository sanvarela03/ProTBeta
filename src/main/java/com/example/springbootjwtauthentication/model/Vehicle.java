package com.example.springbootjwtauthentication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "vehicles"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {
    @Id
    @Column(
            name = "vehicle_id"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "transporter_id"
    )
    private Transporter owner;

}
