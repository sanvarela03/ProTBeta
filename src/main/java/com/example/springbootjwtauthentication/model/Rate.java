package com.example.springbootjwtauthentication.model;

import com.example.springbootjwtauthentication.model.Enum.ERate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "rates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rate implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(STRING)
    @Column(length = 20)
    private ERate name;
    private Double value;
    private String currency;
    private String unitDescription;

    public Rate(ERate name) {
        this.name = name;
    }

    private static final long serialVersionUID = 1L;
}
