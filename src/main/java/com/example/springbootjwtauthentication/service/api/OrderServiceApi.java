package com.example.springbootjwtauthentication.service.api;

import com.example.springbootjwtauthentication.model.*;
import com.example.springbootjwtauthentication.payload.request.OrderProductRequest;
import com.example.springbootjwtauthentication.payload.request.OrderRequest;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.payload.response.OrderResponse;
import com.example.springbootjwtauthentication.payload.response.bing.Resource;
import com.example.springbootjwtauthentication.service.implementations.*;
import com.google.firebase.messaging.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
    private ProductService productService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private OrderStatusManagerService orderStatusManagerService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private BingMapsAsyncService bingMapsAsyncService;

    public ResponseEntity<?> addNewOrder(HttpServletRequest http, OrderRequest request) throws FirebaseMessagingException, InterruptedException, ExecutionException {
        Producer producer = producerService.getProducerById(request.getProducerId());
        Customer customer = customerService.getCustomerByUsername(jwtService.extractUsername(http));
        Address from = producer.getCurrentAddress();
        Address to = addressService.getAddressById(request.getDeliveryAddressId());

        Order order = new Order(customer, producer, from, to);
        orderService.saveOrder(order); //Importante para que no hayan duplicados
        double total = 0.0;
        double totalWeight = 0.0;

        Set<OrderProduct> orderProductSet = new HashSet<>();
        for (OrderProductRequest op : request.getProducts()) {
            Product product = productService.getProductById(op.getProductId());
            boolean isOwner = isOwner(producer, product);
            if (!isOwner) {
                return ResponseEntity.badRequest().body(new MessageResponse("El productor : " + producer.getUsername() + " no es propietario del pruducto con ID: " + product.getId()));
            }
            total += product.getPrice() * op.getUnits();
            totalWeight += product.getWeightPerUnit() * op.getUnits();
            orderProductSet.add(new OrderProduct(order, product, op.getUnits()));
        }
        order.setOrderCost(total);
        order.setOrderWeight(totalWeight);

        List<OrderProduct> orderProductList = orderProductService.saveAllOrderProducts(orderProductSet);

        orderStatusManagerService.created(order);

        notificationService.notifyProducer(customer, producer);
        bingMapsAsyncService.findEstimatedDistance(order);

        return ResponseEntity.ok(new OrderResponse(orderProductList));
    }

    /**
     * Para calcular el costo de envio
     */
    private double calculateShippingCost(Order order) throws InterruptedException, ExecutionException {
        Address from = order.getPickupAddress();
        Address to = order.getDeliveryAddress();
        CompletableFuture<Resource> future = bingMapsAsyncService.getDistance(from.getLatitude(), from.getLongitude(), to.getLatitude(), to.getLongitude());

        Resource resource = future.get();

        double distance = resource.getTravelDistance();
        double time = resource.getTravelDuration();

        order.setEstimatedTravelDuration(time);
        order.setEstimatedTravelDistance(distance);


        return 0;
    }

    private boolean isOwner(Producer producer, Product product) {
        return producer.equals(product.getProducer());
    }
}
