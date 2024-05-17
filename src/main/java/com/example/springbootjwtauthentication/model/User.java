package com.example.springbootjwtauthentication.model;

import com.example.springbootjwtauthentication.payload.request.UpdateFirebaseTokenRequest;
import com.example.springbootjwtauthentication.payload.request.UserInfoRequest;
import com.example.springbootjwtauthentication.payload.response.UserInfoResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.io.Serial;
import java.io.Serializable;
import java.util.*;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(exclude = "currentAddress")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    @JsonIgnore
    private String password;
    private String name;
    private String lastName;
    @JsonIgnore
    private String verificationCode;
    @JsonIgnore
    private Date verificationCodeTimestamp;
    @JsonIgnore
    private String firebaseToken;

    private String phone;

    @ManyToMany(fetch = EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonIgnore
    private Set<Role> roleEntities = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "current_address_id")
    private Address currentAddress;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Producer toProducer() {
        return Producer.builder()
                .name(this.getName())
                .lastName(this.getLastName())
                .username(this.getUsername())
                .fechaDeRegistro((new Date()).toString())//TODO : Complementar
                .email(this.getEmail())
                .phone(this.getPhone())
                .firebaseToken(this.getFirebaseToken())
                .password(this.getPassword())
                .roleEntities(this.getRoleEntities())
                .build();
    }


    public Customer toCustomer() {
        return Customer.builder()
                .name(this.getName())
                .lastName(this.getLastName())
                .username(this.getUsername())
                .email(this.getEmail())
                .phone(this.getPhone())
                .password(this.getPassword())
                .firebaseToken(this.getFirebaseToken())
                .roleEntities(this.getRoleEntities())
                .build();
    }

    public Transporter toTransporter() {
        return Transporter.builder()
                .name(this.getName())
                .lastName(this.getLastName())
                .username(this.getUsername())
                .email(this.getEmail())
                .password(this.getPassword())
                .phone(this.getPhone())
                .firebaseToken(this.getFirebaseToken())
                .roleEntities(this.getRoleEntities())
                .build();
    }

    public UserInfoResponse toUserInfoResponse() {
        return UserInfoResponse.builder()
                .userId(this.getId())
                .username(this.getUsername())
                .name(this.getName())
                .lastName(this.getLastName())
                .email(this.getEmail())
                .phone(this.getPhone())
                .build();
    }

    public void update(UserInfoRequest request) {
        this.setUsername(request.getUsername());
        this.setName(request.getName());
        this.setLastName(request.getLastName());
        this.setEmail(request.getEmail());
        this.setPhone(request.getPhone());
    }

    public void updateFirebaseToken(UpdateFirebaseTokenRequest request) {
        this.setFirebaseToken(request.getFirebaseToken());
    }
}
