package com.example.springbootjwtauthentication.model;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "states"
//        , uniqueConstraints = {@UniqueConstraint(columnNames = "initials")}
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class State {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String initials;

    @ManyToOne
    @JoinColumn(
            name = "country_id",
            referencedColumnName = "id"
    )
    private Country country;
}
