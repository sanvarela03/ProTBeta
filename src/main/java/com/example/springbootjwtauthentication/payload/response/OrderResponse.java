package com.example.springbootjwtauthentication.payload.response;

import com.example.springbootjwtauthentication.model.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private List<OrderProduct> orderProductList;
}
