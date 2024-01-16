package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.Enum.EStatus;
import com.example.springbootjwtauthentication.model.Order;
import com.example.springbootjwtauthentication.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Clase que se encarga de gestionar la transición
 * de estados de un pedido
 */
@Service
public class OrderStatusManagerService {
    @Autowired
    private OrderStatusService orderStatusService;
    @Autowired
    private StatusService statusService;

    public void created(Order order) {
        Status created = statusService.getStatusByName(EStatus.CREATED);
        orderStatusService.createOrderStatus(order, created);
    }

    public void accepted(Order order) {
        Status accepted = statusService.getStatusByName(EStatus.ACCEPTED);
        orderStatusService.createOrderStatus(order, accepted);
    }

    public void transporterAssigned(Order order) {
        Status transporterAssignedState = statusService.getStatusByName(EStatus.TRANSPORTER_ASSIGNED);
        orderStatusService.createOrderStatus(order, transporterAssignedState);
    }

    public void onWayToPickUp(Order order) {
        Status transporterAssignedState = statusService.getStatusByName(EStatus.ON_WAY_TO_PICKUP);
        orderStatusService.createOrderStatus(order, transporterAssignedState);
    }

    public void shipped(Order order) {

    }

    public void onWayToDelivery(Order order) {

    }
}
