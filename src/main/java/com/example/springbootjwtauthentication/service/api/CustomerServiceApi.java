package com.example.springbootjwtauthentication.service.api;

import com.example.springbootjwtauthentication.model.*;
import com.example.springbootjwtauthentication.payload.OrderInfoResponse;
import com.example.springbootjwtauthentication.payload.response.*;
import com.example.springbootjwtauthentication.service.implementations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.springbootjwtauthentication.model.Address.getAddressResponseList;

@Service
@Slf4j
public class CustomerServiceApi {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderProductService orderProductService;

    @Autowired
    private OrderStatusService orderStatusService;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private ProductService productService;

    public ResponseEntity<ProducerDetailResponse> searchProducer(Long id) {
        Producer producer = producerService.getProducerById(id);

        List<Product> products = productService.getAllAvailableProductsByProducerId(id);

        if (products != null && producer.getCurrentAddress() != null) {
            return ResponseEntity.ok(
                    ProducerDetailResponse.builder()
                            .producerSearchResponse(producer.toProducerSearchResponse())
                            .productList(products)
                            .build()
            );
        }
        return null;
    }

    public ResponseEntity<List<ProducerSearchResponse>> searchAllProducers() {

        List<Producer> producerList = producerService.getAllProducers();
        List<ProducerSearchResponse> producerSearchResponseList = getProducerSearchResponseList(producerList);

        return ResponseEntity.ok(producerSearchResponseList);
    }

    public ResponseEntity<List<OrderInfoResponse>> getAllOrderInfo(Long userId) {
        List<Order> orderList = orderService.getAllOrdersByCustomerId(userId);
        List<OrderInfoResponse> orderInfoResponseList = getOrderInfoResponseList(orderList);
        return ResponseEntity.ok().body(orderInfoResponseList);
    }


    public ResponseEntity<CustomerInfoResponse> getCustomer(Long userId) {
        Customer customer = customerService.getCustomerById(userId);
        List<Order> orderList = orderService.getAllOrdersByCustomerId(userId);
        List<Address> addressList = addressService.getAllAddressByUserId(userId);

        List<AddressResponse> addressResponseList = getAddressResponseList(addressList);
        List<OrderInfoResponse> orderInfoResponseList = getOrderInfoResponseList(orderList);

        CustomerInfoResponse customerInfoResponse = customer.toCustomerInfoResponse(addressResponseList, orderInfoResponseList);

        return ResponseEntity.ok().body(customerInfoResponse);
    }

    private List<ProducerSearchResponse> getProducerSearchResponseList(List<Producer> producerList) {
        List<ProducerSearchResponse> producerSearchResponseList = new ArrayList<>();

        producerList.forEach(producer -> {
            if (producer.getCurrentAddress() != null) {
                producerSearchResponseList.add(
                        producer.toProducerSearchResponse()
                );
            }

        });
        return producerSearchResponseList;
    }

    private List<OrderInfoResponse> getOrderInfoResponseList(List<Order> orderList) {
        List<OrderInfoResponse> orderInfoResponseList = new ArrayList<>();

        orderList.forEach(
                order -> {
                    List<StatusResponse> statusResponseList = getStatusResponseList(order);

                    List<ProductResponse> productResponseList = getProductResponseList(order);

                    orderInfoResponseList.add(order.toOrderInfoResponse(statusResponseList, productResponseList));
                }
        );
        return orderInfoResponseList;
    }

//    private List<AddressResponse> getAddressResponseList(List<Address> addressList) {
//        List<AddressResponse> addressResponseList = new ArrayList<>();
//
//        addressList.forEach(
//                address -> {
//                    addressResponseList.add(address.toAddressResponse());
//                }
//        );
//        return addressResponseList;
//    }

    private List<ProductResponse> getProductResponseList(Order order) {
        List<ProductResponse> productResponseList = new ArrayList<>();
        orderProductService.getAllOrderProductsByOrderId(order.getId()).forEach(orderProduct -> {
            productResponseList.add(
                    orderProduct.toProductResponse()
            );
        });
        return productResponseList;
    }

    private List<StatusResponse> getStatusResponseList(Order order) {
        List<StatusResponse> statusResponseList = new ArrayList<>();
        orderStatusService.getAllByOrderId(order.getId()).forEach(orderStatus -> {
                    statusResponseList.add(
                            orderStatus.toStatusResponse()
                    );
                }
        );
        return statusResponseList;
    }
}
