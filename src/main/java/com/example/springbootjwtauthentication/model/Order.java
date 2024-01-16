package com.example.springbootjwtauthentication.model;

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

    private double estimatedTravelDistance;

    private double estimatedTravelDuration;

    private double shippingCost;


    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
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
}
