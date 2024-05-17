package com.example.springbootjwtauthentication.model;

import com.example.springbootjwtauthentication.payload.request.UpdateProductRequest;
import com.example.springbootjwtauthentication.payload.response.ProducerContactInfoResponse;
import com.example.springbootjwtauthentication.payload.response.ProducerDetailResponse;
import com.example.springbootjwtauthentication.payload.response.ProductDetailResponse;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(
        name = "products"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product implements Serializable {

    private static final long serialVersionUID = 1879238L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String description;

    private double weight;
    private double length;
    private double width;
    private double height;

    private double price;
    private int unitsAvailable;
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(
            name = "producer_id"
    )
    @ToString.Exclude
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("producerId")
    private Producer producer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @JsonIgnore
    @ToString.Exclude
    private List<ProductImage> images;

    public Product(String name, int unitsAvailable, String description, double price, boolean isAvailable, Producer producer) {
        this.name = name;
        this.unitsAvailable = unitsAvailable;
        this.description = description;
        this.price = price;
        this.isAvailable = isAvailable;
        this.producer = producer;
    }

//    public ProductDetailResponse toProductDetailResponse() {
//        return
//    }

    public void update(UpdateProductRequest r) {
        this.setName(r.getName());
        this.setDescription(r.getDescription());
        this.setPrice(r.getPrice());
        this.setUnitsAvailable(r.getUnitsAvailable());
        this.setWeight(r.getWeight());
        this.setWidth(r.getWidth() / 100);
        this.setHeight(r.getHeight() / 100);
        this.setLength(r.getLength() / 100);
        this.setAvailable(r.isAvailable());
    }

    public ProducerContactInfoResponse toProducerContactInfoResponse() {
        return ProducerContactInfoResponse.builder()
                .producerId(this.getProducer().getId())
                .completeName(this.getProducer().getName() + " " + this.getProducer().getLastName())
                .email(this.getProducer().getEmail())
                .phone(this.getProducer().getPhone())
                .build();

    }
}
