package com.example.springbootjwtauthentication.model;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "cities")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class City {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(
            name = "state_id",
            referencedColumnName = "id"
    )
    private State state;
}
