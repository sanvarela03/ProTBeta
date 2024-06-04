package com.example.springbootjwtauthentication.service.api;

import com.example.springbootjwtauthentication.model.*;
import com.example.springbootjwtauthentication.payload.request.ProducerAnswerRequest;
import com.example.springbootjwtauthentication.payload.response.AddressResponse;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.payload.response.ProducerInfoResponse;
import com.example.springbootjwtauthentication.service.implementations.*;
import com.example.springbootjwtauthentication.service.interfaces.TransporterAnswerListener;
import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public ResponseEntity<MessageResponse> acceptOrder(HttpServletRequest http, Long orderId, Boolean accepted) throws FirebaseMessagingException {

        Order order = orderService.getOrderById(orderId);

        if (accepted) {
            handleAccepted(order);
            return ResponseEntity.ok(new MessageResponse("Se envió confirmación al comprador. Se está buscando el transportador ..."));
        } else {
            handleRejected();
            return ResponseEntity.ok(new MessageResponse("Se envió confirmación por favor ten en encuenta que no aceptar pedidos nos empobrece a ambos sadge"));
        }
    }

    /**
     * Si el productor acepta el pedido, entonces
     * Notificar al comprador
     * Asignarlo al pedido (NO YA ESTA ASIGNADO) solo hay que cambiar el estado del pedido
     * Actualizar el estado del pedido
     * Buscar al transportador
     */
    protected void handleAccepted(Order order) throws FirebaseMessagingException {

//        Customer customer = order.getCustomer();
//        sendFirebaseNotification(customer, order);
        orderStatusManagerService.accepted(order);

        TransporterAnswerListener transporterAssignmentService = (TransporterAnswerListener) beanFactory.createBean(TransporterAssignmentService.class);

        List<Transporter> transporterList = findTransporters();

        Long chosenTransporterId = order.getChosenTransporterId();

        if (chosenTransporterId != null) {

            Transporter chosenTransporter = transporterService.getById(chosenTransporterId);
            int index = transporterList.indexOf(chosenTransporter);

            if (index != -1) {
                transporterList.remove(index);
            }
            transporterList.add(0, chosenTransporter);
        } else {
            log.info("No se ha seleccionado un transportador elegido.");
        }

        transporterAssignmentService.startJob(assignmentManager, transporterList, order);
    }

    /**
     * Sep esta funcion necesita ser mejorada
     * <p>
     * Buscar transportador en función de su ubicación (distancia mas cercana al pedido)
     */
    private List<Transporter> findTransporters() {
        return transporterService.getAllAvailableTransporters();
    }

    private void sendFirebaseNotification(Customer customer, Order order) {
        //TODO
        notificationService.notifyCustomer(order, customer);
    }


    public ResponseEntity<ProducerInfoResponse> getProducer(Long userId) {
        Producer producer = producerService.getProducerById(userId);
        List<Product> products = productService.getAllProductsByProducerId(userId);
        List<Address> addressList = addressService.getAllAddressByUserId(userId);
        List<AddressResponse> addressResponseList = new ArrayList<>();

        addressList.forEach(
                address -> {
                    addressResponseList.add(address.toAddressResponse());
                }
        );

        ProducerInfoResponse producerInfoResponse = producer.toProducerInfoResponse(addressResponseList, products);
        return ResponseEntity.ok().body(producerInfoResponse);
    }

    /**
     * Si el productor rechaza el pedido, entonces
     * Notificar al comprador con las razones del rechazo
     */
    private void handleRejected() {

    }
}
