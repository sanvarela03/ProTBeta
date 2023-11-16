package com.example.springbootjwtauthentication.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Enumerated(STRING)  @Column(length = 20)
    private ERole name;
    public Role(ERole name) {
        this.name = name;
    }
}
