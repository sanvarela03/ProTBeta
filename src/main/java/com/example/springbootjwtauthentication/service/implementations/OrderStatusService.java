package com.example.springbootjwtauthentication.service.implementations;


import com.example.springbootjwtauthentication.model.Order;
import com.example.springbootjwtauthentication.model.OrderStatus;
import com.example.springbootjwtauthentication.model.Status;
import com.example.springbootjwtauthentication.repository.OrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderStatusService {

    @Autowired
    private OrderStatusRepository repository;

    public OrderStatus createOrderStatus(Order order, Status status) {

        OrderStatus orderStatus = new OrderStatus();

        orderStatus.setOrder(order);
        orderStatus.setStatus(status);
        orderStatus.setCreatedAt(new Date());
        repository.save(orderStatus);

        return orderStatus;
    }

    public List<OrderStatus> getAllByOrderId(Long id) {
        return repository.findAllByOrderId(id).orElseThrow(() -> new RuntimeException("No se encontró los estados para el pedido con ID: " + id));
    }
}
