package com.example.springbootjwtauthentication.service.api;

import com.example.springbootjwtauthentication.model.*;
import com.example.springbootjwtauthentication.payload.request.ProducerAnswerRequest;
import com.example.springbootjwtauthentication.payload.response.ProducerInfoResponse;
import com.example.springbootjwtauthentication.service.implementations.*;
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
    private OrderStatusManagerService orderStatusManagerService;
    @Autowired
    private AutowireCapableBeanFactory beanFactory;
    @Autowired
    private TransporterAssignmentManager assignmentManager;
    @Autowired
    private TransporterService transporterService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ProductService productService;

    @Autowired
    private AddressService addressService;

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
        sendFirebaseNotification(customer, order);

        TransporterAssignmentService transporterAssignmentServiceService = beanFactory.createBean(TransporterAssignmentService.class);

        transporterAssignmentServiceService.startJob(assignmentManager, findTransporters(), order);
    }

    /**
     * Sep esta funcion necesita ser mejorada
     * <p>
     * Buscar transportador en función de su ubicación (distancia mas cercana al pedido)
     */
    private List<Transporter> findTransporters() {
        return transporterService.getAllTransporters();
    }

    private void sendFirebaseNotification(Customer customer, Order order) {
        //TODO
        notificationService.notifyCustomer(order, customer);
    }


    public ResponseEntity<ProducerInfoResponse> getProducer(Long userId) {
        Producer producer = producerService.getProducerById(userId);
        List<Product> products = productService.getAllProductsByProducerId(userId);
        List<Address> addressList = addressService.getAllAddressByUserId(userId);

        ProducerInfoResponse producerInfoResponse = new ProducerInfoResponse();

        producerInfoResponse.setProducerId(producer.getId());
        producerInfoResponse.setName(producer.getName());
        producerInfoResponse.setLastname(producer.getLastName());
        producerInfoResponse.setUsername(producer.getUsername());
        producerInfoResponse.setEmail(producer.getEmail());
        producerInfoResponse.setAddressList(addressList);
        producerInfoResponse.setProductsList(products);

        return ResponseEntity.ok().body(producerInfoResponse);
    }

    /**
     * Si el productor rechaza el pedido, entonces
     * Notificar al comprador con las razones del rechazo
     */
    private void handleRejected() {

    }
}
