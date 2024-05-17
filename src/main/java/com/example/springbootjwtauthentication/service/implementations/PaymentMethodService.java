package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.Enum.EPaymentMethod;
import com.example.springbootjwtauthentication.model.PaymentMethod;
import com.example.springbootjwtauthentication.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodService {
    @Autowired
    private PaymentMethodRepository repository;

    public PaymentMethod getPaymentMethodByName(EPaymentMethod name) {
        return repository.findByName(name).orElseThrow(() -> new RuntimeException("No se encontró el metodo de pago con nombre :" + name.toString()));

    }
}
