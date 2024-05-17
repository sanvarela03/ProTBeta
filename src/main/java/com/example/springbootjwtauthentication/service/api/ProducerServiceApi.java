package com.example.springbootjwtauthentication.service.api;

import com.example.springbootjwtauthentication.model.*;
import com.example.springbootjwtauthentication.payload.request.ProducerAnswerRequest;
import com.example.springbootjwtauthentication.payload.response.AddressResponse;
import com.example.springbootjwtauthentication.payload.response.MessageResponse;
import com.example.springbootjwtauthentication.payload.response.ProducerInfoResponse;
import com.example.springbootjwtauthentication.service.implementations.*;
import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.http.ResponseEntity;
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
        Producer producer = producerService.getProducerByUsername(jwtService.extractUsername(http));
        Order order = orderService.getOrderById(orderId);
        Customer customer = order.getCustomer();

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
    private void handleAccepted(Order order) throws FirebaseMessagingException {

        Customer customer = order.getCustomer();
        orderStatusManagerService.accepted(order);
        sendFirebaseNotification(customer, order);

        TransporterAssignmentService transporterAssignmentServiceService = beanFactory.createBean(TransporterAssignmentService.class);

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

        transporterAssignmentServiceService.startJob(assignmentManager, transporterList, order);
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

        List<AddressResponse> addressResponseList = new ArrayList<>();

        addressList.forEach(
                address -> {
                    addressResponseList.add(
                            AddressResponse.builder()
                                    .id(address.getId())
                                    .name(address.getName())
                                    .street(address.getStreet())
                                    .instruction(address.getInstruction())
                                    .latitude(address.getLatitude())
                                    .longitude(address.getLongitude())
                                    .city(address.getCity().getName())
                                    .state(address.getCity().getState().getName())
                                    .country(address.getCity().getState().getCountry().getName())
                                    .userId(address.getUser().getId())
                                    .build()
                    );
                }
        );

        ProducerInfoResponse producerInfoResponse = new ProducerInfoResponse();


        producerInfoResponse.setProducerId(producer.getId());
        producerInfoResponse.setName(producer.getName());
        producerInfoResponse.setLastname(producer.getLastName());
        producerInfoResponse.setUsername(producer.getUsername());
        producerInfoResponse.setEmail(producer.getEmail());
        producerInfoResponse.setPhone(producer.getPhone());

        if (producer.getCurrentAddress() != null) {
            producerInfoResponse.setCurrentAddressId(producer.getCurrentAddress().getId());
        }
        producerInfoResponse.setAddressList(addressResponseList);
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
