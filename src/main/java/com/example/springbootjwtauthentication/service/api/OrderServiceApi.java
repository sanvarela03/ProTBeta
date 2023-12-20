package com.example.springbootjwtauthentication.service.api;

import com.example.springbootjwtauthentication.model.*;
import com.example.springbootjwtauthentication.model.Enum.EState;
import com.example.springbootjwtauthentication.payload.request.OrderProductRequest;
import com.example.springbootjwtauthentication.payload.request.OrderRequest;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.payload.response.OrderResponse;
import com.example.springbootjwtauthentication.service.implementations.*;
import com.google.firebase.messaging.*;
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
    @Autowired
    private FirebaseMessaging firebaseMessaging;
    @Autowired
    private AddressService addressService;

    public ResponseEntity<?> addNewOrder(HttpServletRequest http, OrderRequest request) throws FirebaseMessagingException {
        Producer producer = producerService.getProducerById(request.getProducerId());
        Customer customer = customerService.getCustomerByUsername(jwtService.extractUsername(http));

        Order order = new Order(customer, producer);

        Set<OrderProduct> orderProductSet = new HashSet<>();
        double total = 0.0;

        for (OrderProductRequest op : request.getProducts()) {
            Product product = productService.getProductById(op.getProductId());
            boolean isOwner = isOwner(producer, product);
            if (!isOwner) {
                return ResponseEntity.badRequest().body(new MessageResponse("El productor : " + producer.getUsername() + " no es propietario del pruducto con ID: " + product.getId()));
            }
            total += product.getPrice() * op.getUnits();
            orderProductSet.add(new OrderProduct(order, product, op.getUnits()));
        }

        Address from = producer.getCurrentAddress();
        Address to = addressService.getAddressById(request.getDeliveryAddressId());

        order.setPickupAddress(from);
        order.setDeliveryAddress(to);

        order.setOrderCost(total);
        order.setShippingCost(calculateShippingCost());
        orderService.saveOrder(order);

        orderStateService.createOrderState(order, stateService.getSateByName(EState.CREATED));
        List<OrderProduct> orderProductList = orderProductService.saveAllOrderProducts(orderProductSet);

//        notifyProducer(customer, producer);
        return ResponseEntity.ok(new OrderResponse(orderProductList));
    }

    /**
     * Para calcular el costo de envio
     */
    private double calculateShippingCost() {


        return 0;
    }

    private void notifyProducer(Customer customer, Producer producer) throws FirebaseMessagingException {

        Notification notification = Notification.builder()
                .setTitle("Solicitud de pedido")
                .setBody(customer.getName() + " ha realizado un pedido")
                .build();


        String registrationToken = "d1N9j1msRraUfigR5m9hoT:APA91bHx1IYT5ia2isW0GHeuI-RmDRaYLaiyLkEmMPJ0PCbfdWYRM1cdPLqSmCu-yosuT-JFZtkbGCBH-YSHxj6q3E0O0A52oKlxOHBoG-r6f0G4cF-8Gxxo-K-6Zn5lFI0eVSnAkr28";
//      String registrationToken = producer.getFirebaseToken();
        Message msg = Message.builder()
                .setToken(registrationToken)
                .setNotification(notification)
                .setAndroidConfig(AndroidConfig.builder()
                        .setNotification(AndroidNotification.builder()
                                .setSound("default")
                                .setClickAction("YOUR_CLICK_ACTION")
                                .setDefaultLightSettings(true)
                                .setColor("#fca103")
                                .setBodyLocalizationKey("")
                                .build())
                        .build())
                .build();

        String id = firebaseMessaging.send(msg);
    }

    private boolean isOwner(Producer producer, Product product) {
        return producer.equals(product.getProducer());
    }
}
