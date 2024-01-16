package com.example.springbootjwtauthentication.service.implementations;


import com.example.springbootjwtauthentication.model.Customer;
import com.example.springbootjwtauthentication.model.Order;
import com.example.springbootjwtauthentication.model.Producer;
import com.example.springbootjwtauthentication.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public Order saveOrder(Order order) {
        return repository.save(order);
    }

    public Order getOrderById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("No se encontró el pedido con ID: " + id)
        );
    }
}
