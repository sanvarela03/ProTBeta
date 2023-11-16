package com.example.springbootjwtauthentication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "current_positions"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String latitude;
    private String longitude;

    @OneToOne @JoinColumn(name = "transporter_id", referencedColumnName = "id")
    private Transporter transporter;
}
