package com.example.springbootjwtauthentication.payload;

import com.example.springbootjwtauthentication.model.Address;
import com.example.springbootjwtauthentication.payload.response.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderInfoResponse {
    private Long orderId;

    private double orderCost;

    private double orderWeight;

    private double orderVolume;

    private double estimatedTravelDistance;

    private double estimatedTravelDuration;

    private double shippingCost;

    private String paymentMethod;

    private Date maxDeliveryDate;

    private Date estimatedPickupDate;

    private CustomerContactInfoResponse customerContactInfoResponse;

    private ProducerContactInfoResponse producerContactInfoResponse;

    private TransporterContactInfoResponse transporterContactInfoResponse;

    private AddressResponse pickupAddress;

    private AddressResponse deliveryAddress;

    private List<StatusResponse> statusList;

    private List<ProductResponse> items;
}
