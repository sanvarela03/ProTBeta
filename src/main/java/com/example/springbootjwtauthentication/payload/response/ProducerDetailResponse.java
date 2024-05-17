package com.example.springbootjwtauthentication.payload.response;

import com.example.springbootjwtauthentication.model.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProducerDetailResponse {
    private ProducerSearchResponse producerSearchResponse;
    private List<Product> productList;

}
