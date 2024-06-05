package com.example.springbootjwtauthentication.model;

import com.example.springbootjwtauthentication.payload.request.UpdateAddressRequest;
import com.example.springbootjwtauthentication.payload.response.AddressResponse;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String street;
    private String instruction;
    private double latitude;
    private double longitude;

    @ManyToOne
    @JoinColumn(
            name = "city_id",
            referencedColumnName = "id"
    )
    @ToString.Exclude
    private City city;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    @ToString.Exclude
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("userId")
    private User user;

    @Serial
    private static final long serialVersionUID = 1L;


    public AddressResponse toAddressResponse() {
        return AddressResponse.builder()
                .id(this.getId())
                .name(this.getName())
                .street(this.getStreet())
                .instruction(this.getInstruction())
                .latitude(this.getLatitude())
                .longitude(this.getLongitude())
                .city(this.getCity().getName())
                .state(this.getCity().getState().getName())
                .country(this.getCity().getState().getCountry().getName())
                .userId(this.getUser().getId())
                .build();
    }

    public void update(UpdateAddressRequest request) {
        this.setName(request.getName());
        this.setStreet(request.getStreet());
        this.setInstruction(request.getInstruction());
        this.setLatitude(request.getLatitude());
        this.setLongitude(request.getLongitude());
    }

    public static List<AddressResponse> getAddressResponseList(List<Address> addressList) {
        List<AddressResponse> addressResponseList = new ArrayList<>();

        addressList.forEach(
                address -> {
                    addressResponseList.add(address.toAddressResponse());
                }
        );
        return addressResponseList;
    }

    public String getAddressResume() {
        return this.getStreet() + ", " + this.getCity().getName() + ", " + this.getLatitude() + ", " + this.getLongitude();
    }
}
