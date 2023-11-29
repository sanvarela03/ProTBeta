package com.example.springbootjwtauthentication.service.implementations;


import com.example.springbootjwtauthentication.model.Order;
import com.example.springbootjwtauthentication.model.OrderState;
import com.example.springbootjwtauthentication.model.State;
import com.example.springbootjwtauthentication.repository.OrderStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderStateService {

    @Autowired
    private OrderStateRepository repository;

    public OrderState createOrderState(Order order, State state) {
        return repository.save(new OrderState(order, state));
    }
}
