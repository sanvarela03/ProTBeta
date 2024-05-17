package com.example.springbootjwtauthentication.service.implementations;


import com.example.springbootjwtauthentication.model.Address;
import com.example.springbootjwtauthentication.model.Customer;
import com.example.springbootjwtauthentication.model.Order;
import com.example.springbootjwtauthentication.model.Producer;
import com.example.springbootjwtauthentication.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public List<Order> getAllOrdersByProducerId(Long userId) {
        return repository.findAllByProducer_Id(userId).orElseThrow(() -> new RuntimeException("No se pudo encontrar los pedidos asociados con el productor con id: " + userId));
    }

    public List<Order> getAllOrdersByCustomerId(Long userId) {
        return repository.findAllByCustomer_Id(userId).orElseThrow(() -> new RuntimeException("No se pudo encontrar los pedidos asociados con el comprador con id: " + userId));
    }

    public List<Order> getAllOrdersByTransporterId(Long userId) {
        return repository.findAllByTransporter_Id(userId).orElseThrow(() -> new RuntimeException("No se pudo encontrar los pedidos asociados con el transportador con id: " + userId));
    }
}
