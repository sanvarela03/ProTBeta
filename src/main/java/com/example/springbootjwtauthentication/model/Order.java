package com.example.springbootjwtauthentication.model;

import com.example.springbootjwtauthentication.payload.OrderInfoResponse;
import com.example.springbootjwtauthentication.payload.response.*;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import java.text.ParseException;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "customer_id",
            referencedColumnName = "id"
    )
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("customerId")
    private Customer customer;

    @ManyToOne
    @JoinColumn(
            name = "producer_id",
            referencedColumnName = "id"
    )
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("producerId")
    private Producer producer;

    @ManyToOne
    @JoinColumn(
            name = "transporter_id",
            referencedColumnName = "id"
    )
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("transporterId")
    private Transporter transporter;

    @Temporal(TemporalType.TIMESTAMP)
    private Date estimatedDeliveryDate;

    private double orderCost;

    private double orderWeight;

    private double orderVolume;

    private Double estimatedTravelDistance;

    private Double estimatedTravelDuration;

    private double shippingCost;

    private Long chosenTransporterId;

    @ManyToOne(fetch = FetchType.LAZY)
    private PaymentMethod paymentMethod;

    @Temporal(TemporalType.TIMESTAMP)
    private Date maxDeliveryDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date estimatedPickupDate;

    @ManyToOne
    @JoinColumn(name = "pickup_address_id")
    private Address pickupAddress;

    @ManyToOne
    @JoinColumn(name = "delivery_address_id")
    private Address deliveryAddress;


    private String deliveryVerificationCode;
    @JsonIgnore
    private Date deliveryVerificationCodeTimestamp;

    public Order(Customer customer, Producer producer) {
        this.customer = customer;
        this.producer = producer;
    }

    @PrePersist
    public void prePersist() {
        // Calcular la fecha máxima de entrega (fecha actual + 7 días)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        maxDeliveryDate = calendar.getTime();
    }

    public Order(Customer customer, Producer producer, Address from, Address to) {
        this.customer = customer;
        this.producer = producer;
        this.pickupAddress = from;
        this.deliveryAddress = to;
    }

    public void setEstimatedDeliveryDateFromString(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date parsedDate = dateFormat.parse(dateString);
        this.estimatedDeliveryDate = parsedDate;
    }

    public void setEstimatedPickupDateFromString(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date parsedDate = dateFormat.parse(dateString);
        this.estimatedPickupDate = parsedDate;
    }

    public OrderInfoResponse toOrderInfoResponse(
            List<StatusResponse> statusResponseList,
            List<ProductResponse> productResponseList
    ) {
        OrderInfoResponse newOrderInfoResponse = new OrderInfoResponse();


        newOrderInfoResponse.setOrderId(this.getId());
        newOrderInfoResponse.setCustomerContactInfoResponse(getCustomerContactInfoResponse());
        newOrderInfoResponse.setProducerContactInfoResponse(getProducerContactInfoResponse());
        newOrderInfoResponse.setTransporterContactInfoResponse(getTransporterContactInfoResponse());
        newOrderInfoResponse.setOrderCost(this.getOrderCost());
        newOrderInfoResponse.setOrderWeight(this.getOrderWeight());
        newOrderInfoResponse.setOrderVolume(this.getOrderVolume());
        newOrderInfoResponse.setEstimatedTravelDistance(this.getEstimatedTravelDistance());
        newOrderInfoResponse.setEstimatedTravelDuration(this.getEstimatedTravelDuration());
        newOrderInfoResponse.setShippingCost(this.getShippingCost());
        if (this.getPaymentMethod() != null) {
            newOrderInfoResponse.setPaymentMethod(this.getPaymentMethod().getName().toString());
        }
        newOrderInfoResponse.setMaxDeliveryDate(this.getMaxDeliveryDate());
        newOrderInfoResponse.setEstimatedPickupDate(this.getEstimatedPickupDate());
        newOrderInfoResponse.setPickupAddress(this.getPickupAddress().toAddressResponse());
        newOrderInfoResponse.setDeliveryAddress(this.getDeliveryAddress().toAddressResponse());
        newOrderInfoResponse.setStatusList(statusResponseList);
        newOrderInfoResponse.setItems(productResponseList);


        return newOrderInfoResponse;
    }

    private TransporterContactInfoResponse getTransporterContactInfoResponse() {
        TransporterContactInfoResponse newTransporterContactInfo = new TransporterContactInfoResponse();
        if (this.getTransporter() != null) {
            newTransporterContactInfo.setTransporterId(this.getTransporter().getId());
            newTransporterContactInfo.setCompleteName(this.getTransporter().getName() + " " + this.getTransporter().getLastName());
            newTransporterContactInfo.setPhone(this.getTransporter().getPhone());
            newTransporterContactInfo.setEmail(this.getTransporter().getEmail());
        }
        return newTransporterContactInfo;
    }

    private ProducerContactInfoResponse getProducerContactInfoResponse() {
        ProducerContactInfoResponse newProducerContactInfo = new ProducerContactInfoResponse();
        newProducerContactInfo.setProducerId(this.getProducer().getId());
        newProducerContactInfo.setCompleteName(this.getProducer().getName() + " " + this.getProducer().getLastName());
        newProducerContactInfo.setPhone(this.getProducer().getPhone());
        newProducerContactInfo.setEmail(this.getProducer().getEmail());
        return newProducerContactInfo;
    }

    private CustomerContactInfoResponse getCustomerContactInfoResponse() {
        CustomerContactInfoResponse newCustomerContactInfo = new CustomerContactInfoResponse();
        newCustomerContactInfo.setCustomerId(this.getCustomer().getId());
        newCustomerContactInfo.setCompleteName(this.getCustomer().getName() + " " + this.getCustomer().getLastName());
        newCustomerContactInfo.setPhone(this.getCustomer().getPhone());
        newCustomerContactInfo.setEmail(this.getCustomer().getEmail());
        return newCustomerContactInfo;
    }


}
