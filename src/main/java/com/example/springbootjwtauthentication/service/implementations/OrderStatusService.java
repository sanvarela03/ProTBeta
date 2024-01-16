package com.example.springbootjwtauthentication.service.implementations;


import com.example.springbootjwtauthentication.model.Order;
import com.example.springbootjwtauthentication.model.OrderStatus;
import com.example.springbootjwtauthentication.model.Status;
import com.example.springbootjwtauthentication.repository.OrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusService {

    @Autowired
    private OrderStatusRepository repository;

    public OrderStatus createOrderStatus(Order order, Status status) {
        return repository.save(new OrderStatus(order, status));
    }
}
