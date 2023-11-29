package com.example.springbootjwtauthentication.service.api;

import com.example.springbootjwtauthentication.model.*;
import com.example.springbootjwtauthentication.model.Enum.EState;
import com.example.springbootjwtauthentication.payload.request.OrderProductRequest;
import com.example.springbootjwtauthentication.payload.request.OrderRequest;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.payload.response.OrderResponse;
import com.example.springbootjwtauthentication.service.implementations.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class OrderServiceApi {
    @Autowired
    private ProducerService producerService;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderProductService orderProductService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private StateService stateService;
    @Autowired
    private OrderStateService orderStateService;
    @Autowired
    private ProductService productService;

    public ResponseEntity<?> addNewOrder(HttpServletRequest http, OrderRequest request) {
        Producer producer = producerService.getProducerById(request.getProducerId());
        Customer customer = customerService.getCustomerByUsername(jwtService.extractUsername(http));

        Order order = orderService.createOrder(customer, producer);
        orderStateService.createOrderState(order, stateService.getSateByName(EState.CREATED));

        Set<OrderProduct> orderProductSet = new HashSet<>();

        for (OrderProductRequest op : request.getProducts()) {
            Product product = productService.getProductById(op.getProductId());
            if (!isOwner(producer, product)) {
                return ResponseEntity.badRequest().body(new MessageResponse("El productor : " + producer.getUsername() + " no es propietario del pruducto con ID: " + product.getId()));
            }
            orderProductSet.add(new OrderProduct(order, product, op.getUnits()));
        }

        List<OrderProduct> orderProductList = orderProductService.saveAllOrderProducts(orderProductSet);

        return ResponseEntity.ok(new OrderResponse(orderProductList));
    }
    private boolean isOwner(Producer producer, Product product) {
        return producer.equals(product.getProducer());
    }
}
