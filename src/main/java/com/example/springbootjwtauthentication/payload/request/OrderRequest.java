package com.example.springbootjwtauthentication.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    @NotBlank
    private Long producerId;
    @NotBlank
    private List<OrderProductRequest> products;
}


