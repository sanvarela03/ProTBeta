package com.example.springbootjwtauthentication.payload.response;

import com.example.springbootjwtauthentication.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsResponse {
    private List<Product> products;
}
