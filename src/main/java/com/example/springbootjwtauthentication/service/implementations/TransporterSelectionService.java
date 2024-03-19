package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.Address;
import com.example.springbootjwtauthentication.model.Order;
import com.example.springbootjwtauthentication.model.Transporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransporterSelectionService {

    @Autowired
    private TransporterService transporterService;

    public List<Transporter> getCandidatesList(Order order) {
        Address from = order.getPickupAddress();
        Address to = order.getDeliveryAddress();

        return null; // TODO
    }
}
