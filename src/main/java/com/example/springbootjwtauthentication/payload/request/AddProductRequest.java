package com.example.springbootjwtauthentication.payload.request;


import com.example.springbootjwtauthentication.model.Producer;
import com.example.springbootjwtauthentication.model.Product;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private double price;
    @NotBlank
    private String currency;
    @NotBlank
    private double weightPerUnit_kg;
    @NotBlank
    private double length_cm;
    @NotBlank
    private double width_cm;
    @NotBlank
    private double height_cm;
    @NotBlank
    private int unitsAvailable;

    private boolean available;

    public Product toProduct(Producer producer) {
        return Product.builder()
                .name(this.getName())
                .unitsAvailable(this.getUnitsAvailable())
                .description(this.getDescription())
                .weight(this.getWeightPerUnit_kg())
                .height(this.getHeight_cm() / 100)
                .width(this.getWidth_cm() / 100)
                .length(this.getLength_cm() / 100)
                .price(this.getPrice())
                .isAvailable(this.isAvailable())
                .producer(producer)
                .build();
    }
}
