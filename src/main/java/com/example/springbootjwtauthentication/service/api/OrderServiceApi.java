package com.example.springbootjwtauthentication.service.api;

import com.example.springbootjwtauthentication.model.*;
import com.example.springbootjwtauthentication.model.Enum.EPaymentMethod;
import com.example.springbootjwtauthentication.payload.request.OrderProductRequest;
import com.example.springbootjwtauthentication.payload.request.OrderRequest;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.service.implementations.*;
import com.example.springbootjwtauthentication.service.implementations.dto.TravelCost;
import com.google.firebase.messaging.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Dos casos de uso:
 * <p>
 * 1. Calcular precio de envío del pedido
 * 2. Agregar pedido nuevo (incluye el caso de uso 1)
 */
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
    private BingMapsTravelService bingMapsTravelService;
    @Autowired
    private GasEstimatorService gasEstimatorService;
    @Autowired
    private PaymentMethodService paymentMethodService;
    @Autowired
    private UserService userService;

    public ResponseEntity<?> addNewOrder(HttpServletRequest http, OrderRequest request) throws FirebaseMessagingException, InterruptedException, ExecutionException {
        Producer producer = getProducer(request);
        Customer customer = getCustomer(http);
        Address from = getFrom(producer);
        Address to = getTo(request);

        Order order = new Order(customer, producer, from, to);
        order.setChosenTransporterId(request.getChosenTransporterId());
        orderService.saveOrder(order); // Importante guardarlo acá para que no hayan duplicados, ya que al guardarlo se genera la llave primaria

        Set<OrderProduct> orderProductSet = new HashSet<>();
        double totalPrice = 0.0;
        double totalWeight = 0.0;
        double totalVolume = 0.0;

        for (OrderProductRequest op : request.getProducts()) {
            Product product = productService.getProductById(op.getProductId());
            boolean isOwner = isOwner(producer, product);
            if (!isOwner) {
                return ResponseEntity.badRequest().body(new MessageResponse("El productor : " + producer.getUsername() + " no es propietario del pruducto con ID: " + product.getId()));
            }
            totalPrice += getTotalPrice(product, op);
            totalWeight += getTotalWeight(product, op);
            totalVolume += getTotalVolume(product, op);
            orderProductSet.add(new OrderProduct(order, product, op.getUnits()));
        }
        order.setOrderCost(totalPrice);
        order.setOrderWeight(totalWeight);
        order.setOrderVolume(totalVolume);

        List<OrderProduct> orderProductList = orderProductService.saveAllOrderProducts(orderProductSet);

        setPaymentMethod(request, order);

        orderStatusManagerService.created(order); //TODO PROBLEMA AQUI
        notificationService.notifyProducer(order); // ESTO ES SOLO PARA ESTE CASO DE USO, ES DECIR CUANDO EL COMPRADOR LLEVA A CABO EL PEDIDO
        return ResponseEntity.ok(new MessageResponse("Pedido agregado correctamente"));
    }

    private void setPaymentMethod(OrderRequest request, Order order) {
        switch (request.getPaymentMethod()) {
            case "p0": {
                PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodByName(EPaymentMethod.CASH_ON_DELIVERY);
                order.setPaymentMethod(paymentMethod);
            }
            break;

            case "p1": {
                PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodByName(EPaymentMethod.CREDIT_CARD);
                order.setPaymentMethod(paymentMethod);
            }
            break;
        }
    }

    private double getTotalVolume(Product product, OrderProductRequest op) {
        return product.getHeight() * product.getLength() * product.getWidth() * op.getUnits();
    }

    private Address getTo(OrderRequest request) {
        return addressService.getAddressById(request.getDeliveryAddressId());
    }

    private Address getFrom(Producer producer) {
        return producer.getCurrentAddress();
    }

    private Customer getCustomer(HttpServletRequest http) {
        return customerService.getCustomerByUsername(jwtService.extractUsername(http));
    }

    private Producer getProducer(OrderRequest request) {
        return producerService.getProducerById(request.getProducerId());
    }

    private double getTotalPrice(Product p, OrderProductRequest op) {
        double unitPrice = p.getPrice();
        double units = op.getUnits();
        return unitPrice * units;
    }

    private double getTotalWeight(Product p, OrderProductRequest op) {
        double unitWeight = p.getWeight();
        double units = op.getUnits();
        return unitWeight * units;
    }

    public ResponseEntity<TravelCost> calculateShippingCostRange(Long fromId, HttpServletRequest http) throws InterruptedException, ExecutionException {
        Customer customer = customerService.getCustomerByUsername(jwtService.extractUsername(http));
        Address from = addressService.getAddressById(fromId);
        Address to = customer.getCurrentAddress();
        TravelCost travelCost = gasEstimatorService.getGasConsumptionCost(from, to);
        return ResponseEntity.ok(travelCost);
    }

    public ResponseEntity<TravelCost> calculateShippingCostRange(Long fromId, Long toId) throws InterruptedException, ExecutionException {
        User fromUser = userService.getUserById(fromId);
        User toUser = userService.getUserById(toId);


        Address from = fromUser.getCurrentAddress();
        Address to = toUser.getCurrentAddress();
        TravelCost travelCost = gasEstimatorService.getGasConsumptionCost(from, to);
        return ResponseEntity.ok(travelCost);
    }

    private boolean isOwner(Producer producer, Product product) {
        return producer.equals(product.getProducer());
    }
}
