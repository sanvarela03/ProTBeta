package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.Order;
import com.example.springbootjwtauthentication.model.OrderProduct;
import com.example.springbootjwtauthentication.model.Product;
import com.example.springbootjwtauthentication.payload.request.OrderProductRequest;
import com.example.springbootjwtauthentication.repository.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class OrderProductService {

    @Autowired
    private OrderProductRepository repository;

    public List<OrderProduct> saveAllOrderProducts(Set<OrderProduct> orderProductSet) {
        return repository.saveAll(orderProductSet);
    }

    public OrderProduct createOrderProduct(Order order, Product product, int units) {
        return repository.save(new OrderProduct(order, product, units));
    }
}
