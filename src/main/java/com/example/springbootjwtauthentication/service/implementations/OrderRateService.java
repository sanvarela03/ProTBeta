package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.OrderRate;
import com.example.springbootjwtauthentication.repository.OrderRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderRateService {

    @Autowired
    private OrderRateRepository repository;

    public OrderRate create(OrderRate orderRate) {
        return repository.save(orderRate);
    }
}

