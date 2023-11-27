package com.example.springbootjwtauthentication.model;

import com.example.springbootjwtauthentication.model.Enum.EState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.AUTO;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "states")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class State implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(STRING)
    @Column(length = 20)
    private EState name;

    public State(EState name) {
        this.name = name;
    }

}
