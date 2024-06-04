package com.example.springbootjwtauthentication.payload.response;

import com.example.springbootjwtauthentication.payload.OrderInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransporterInfoResponse {
    private Long transporterId;
    private String username;
    private String name;
    private String lastname;
    private String email;
    private Long currentAddressId;
    private Long currentVehicleId;
    private Boolean available;
    private List<AddressResponse> addressList;
    private List<VehicleResponse> vehicleList;
    private List<OrderInfoResponse> orderInfoResponseList;
}
