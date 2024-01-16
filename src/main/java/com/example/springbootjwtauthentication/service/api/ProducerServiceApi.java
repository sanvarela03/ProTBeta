package com.example.springbootjwtauthentication.service.api;

import com.example.springbootjwtauthentication.model.Customer;
import com.example.springbootjwtauthentication.model.Order;
import com.example.springbootjwtauthentication.model.Producer;
import com.example.springbootjwtauthentication.model.Transporter;
import com.example.springbootjwtauthentication.payload.request.ProducerAnswerRequest;
import com.example.springbootjwtauthentication.service.implementations.*;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProducerServiceApi {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private ProducerService producerService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private FirebaseMessaging firebaseMessaging;
    @Autowired
    private OrderStatusManagerService orderStatusManagerService;
    @Autowired
    private AutowireCapableBeanFactory beanFactory;
    @Autowired
    private TransporterAssignmentManager assignmentManager;
    @Autowired
    private TransporterService transporterService;

    public ResponseEntity<?> acceptOrder(HttpServletRequest http, ProducerAnswerRequest request) throws FirebaseMessagingException {
        Producer producer = producerService.getProducerByUsername(jwtService.extractUsername(http));
        Order order = orderService.getOrderById(request.getOrderId());
        Customer customer = order.getCustomer();

        if (request.isAccepted()) {
            handleAccepted(order);
            return ResponseEntity.ok("Se envió confirmación al comprador. Se está buscando el transportador ...");
        } else {
            handleRejected();
            return ResponseEntity.ok("Se envió confirmación por favor ten en encuenta que no aceptar pedidos nos empobrece a ambos sadge");
        }
    }

    /**
     * Si el productor acepta el pedido, entonces
     * Notificar al comprador
     * Asignarlo al pedido (NO YA ESTA ASIGNADO) solo hay que cambiar el estado del pedido
     * Actualizar el estado del pedido
     * Buscar al transportador
     */
    private void handleAccepted(Order order) throws FirebaseMessagingException {
        Customer customer = order.getCustomer();
        orderStatusManagerService.accepted(order);
        sendFirebaseNotificaction(customer, order);

        TransporterAssignmentService transporterAssignmentServiceService = beanFactory.createBean(TransporterAssignmentService.class);

        transporterAssignmentServiceService.startJob(assignmentManager, findTransporters(), order);
    }

    /**
     * Sep esta funcion necesita ser mejorada
     *
     * Buscar transportador en función de su ubicación (distancia mas cercana al pedido)
     * */
    private List<Transporter> findTransporters() {
        return transporterService.getAllTransporters();
    }

    private void sendFirebaseNotificaction(Customer customer, Order order) {
        //TODO
        log.info("SIMULACRO DE NOTIFICACION AL COMPRADOR ...");
    }

    /**
     * Si el productor rechaza el pedido, entonces
     * Notificar al comprador con las razones del rechazo
     */
    private void handleRejected() {

    }
}
