package com.example.springbootjwtauthentication.service;

import com.example.springbootjwtauthentication.model.*;
import com.example.springbootjwtauthentication.model.Enum.EState;
import com.example.springbootjwtauthentication.payload.request.OrderProductRequest;
import com.example.springbootjwtauthentication.payload.request.OrderRequest;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.payload.response.OrderResponse;
import com.example.springbootjwtauthentication.repository.*;
import com.example.springbootjwtauthentication.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderStateRepository orderStateRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    public ResponseEntity<OrderResponse> addNewOrder(HttpServletRequest http, OrderRequest request) {
        Producer producer = findProducer(request.getProducerId());
        Customer customer = findCustomer(http);
        Order newOrder = createNewOrder(producer, customer);

        Set<OrderProduct> orderProductSet = getOrderProductSet(newOrder, getProductUnits(request.getProducts()));
        List<OrderProduct> orderProductList = orderProductRepository.saveAll(orderProductSet);
        log.info("orderProductList : {}", orderProductList);

        return ResponseEntity.ok(new OrderResponse(orderProductList));
    }

    private Producer findProducer(Long id) {
        return producerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontrór el productor con ID: " + id));
    }

    private Customer findCustomer(HttpServletRequest http) {
        String username = jwtUtils.getUserNameFromJwtToken(http);
        log.info("S_Username: {}", username);
        return customerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No se econtró el cliente con usuario: " + username));
    }

    private Order createNewOrder(
            Producer producer,
            Customer customer
    ) {
        Order order = new Order(customer, producer);
        orderRepository.save(order);


        State state =
                stateRepository
                        .findByName(EState.CREATED)
                        .orElseThrow(() -> new RuntimeException("Estado no encontrado: " + EState.CREATED));

        OrderState orderState = new OrderState(order, state);
        orderStateRepository.save(orderState);
        return order;
    }

    private Set<OrderProduct> getOrderProductSet(Order order, Set<ProductUnits> productUnitSet) {
        Set<OrderProduct> orderProductSet = new HashSet<>();
        for (ProductUnits productUnit : productUnitSet) {
            orderProductSet.add(
                    new OrderProduct(order, productUnit.getProduct(), productUnit.getUnits())
            );
        }
        return orderProductSet;
    }

    private Set<ProductUnits> getProductUnits(List<OrderProductRequest> products) {
        Set<ProductUnits> productUnitsSet = new HashSet<>();

        for (OrderProductRequest op : products) {
            Product product = productRepository.findById(op.getProductId())
                    .orElseThrow(() -> new RuntimeException("No se encontró el producto con ID: " + op.getProductId()));
            productUnitsSet.add(
                    ProductUnits.builder()
                            .product(product)
                            .units(op.getUnits())
                            .build()
            );
        }
        return productUnitsSet;
    }

    @Data
    @AllArgsConstructor
    @Builder
    private static class ProductUnits {
        private Integer units;
        private Product product;
    }
}
