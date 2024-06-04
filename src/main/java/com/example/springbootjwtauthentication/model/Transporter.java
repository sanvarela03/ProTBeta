package com.example.springbootjwtauthentication.model;


import com.example.springbootjwtauthentication.payload.OrderInfoResponse;
import com.example.springbootjwtauthentication.payload.response.AddressResponse;
import com.example.springbootjwtauthentication.payload.response.CustomerInfoResponse;
import com.example.springbootjwtauthentication.payload.response.TransporterInfoResponse;
import com.example.springbootjwtauthentication.payload.response.VehicleResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "transporters"
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Transporter extends User {
    private String fechaDeRegistro;
    private boolean isAvailable;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "current_vehicle_id")
    private Vehicle currentVehicle;

    public TransporterInfoResponse toTransporterInfoResponse(
            List<AddressResponse> addressResponseList,
            List<OrderInfoResponse> orderInfoResponseList,
            List<VehicleResponse> vehicleList
    ) {
        TransporterInfoResponse transporterInfoResponse = new TransporterInfoResponse();
        transporterInfoResponse.setTransporterId(this.getId());
        transporterInfoResponse.setUsername(this.getUsername());
        transporterInfoResponse.setName(this.getName());
        transporterInfoResponse.setLastname(this.getLastName());
        transporterInfoResponse.setEmail(this.getEmail());
        transporterInfoResponse.setAvailable(this.isAvailable());

        if (this.getCurrentAddress() != null) {
            transporterInfoResponse.setCurrentAddressId(this.getCurrentAddress().getId());
        }
        if (this.getCurrentVehicle() != null) {
            transporterInfoResponse.setCurrentVehicleId(this.getCurrentVehicle().getId());
        }
        transporterInfoResponse.setAddressList(addressResponseList);
        transporterInfoResponse.setVehicleList(vehicleList);
        transporterInfoResponse.setOrderInfoResponseList(orderInfoResponseList);

        return transporterInfoResponse;
    }
}
