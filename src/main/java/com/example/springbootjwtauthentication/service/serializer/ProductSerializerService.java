package com.example.springbootjwtauthentication.service.serializer;

import com.example.springbootjwtauthentication.model.Producer;
import com.example.springbootjwtauthentication.model.Product;
import com.example.springbootjwtauthentication.payload.request.AddProductRequest;
import com.example.springbootjwtauthentication.payload.request.UpdateProductRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductSerializerService {
    public Product getProduct(AddProductRequest request, Producer producer) {
        return Product.builder()
                .name(request.getName())
                .unitsAvailable(request.getUnitsAvailable())
                .description(request.getDescription())
                .weight(request.getWeightPerUnit_kg())
                .height(request.getHeight_cm()/100)
                .width(request.getWidth_cm()/100)
                .length(request.getLength_cm()/100)
                .price(request.getPrice())
                .isAvailable(true)
                .producer(producer)
                .build();
    }

    public void updateSetup(UpdateProductRequest request, Product product) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setUnitsAvailable(request.getUnitsAvailable());
        product.setWeight(request.getWeight());
        product.setAvailable(request.isAvailable());
    }
}
