package com.example.springbootjwtauthentication.service.api;

import com.example.springbootjwtauthentication.model.*;
import com.example.springbootjwtauthentication.payload.OrderInfoResponse;
import com.example.springbootjwtauthentication.payload.request.PickupRequest;
import com.example.springbootjwtauthentication.payload.response.*;
import com.example.springbootjwtauthentication.service.api.auth.VerificationCodeService;
import com.example.springbootjwtauthentication.service.implementations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.springbootjwtauthentication.model.Address.getAddressResponseList;
import static com.example.springbootjwtauthentication.model.Vehicle.getVehicleResponseList;

@Service
@Slf4j
public class TransporterServiceApi {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private TransporterService transporterService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderStatusService orderStatusService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private OrderProductService orderProductService;
    @Autowired
    private OrderStatusManagerService orderStatusManagerService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private VerificationCodeService verificationCodeService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private EmailService emailService;

    public ResponseEntity<TransporterInfoResponse> getTransporter(Long userId) {
        Transporter transporter = transporterService.getById(userId);
        List<Order> orderList = orderService.getAllOrdersByTransporterId(userId);
        List<Address> addressList = addressService.getAllAddressByUserId(userId);
        List<Vehicle> vehicleList = vehicleService.getAllVehiclesByTransporterId(userId);

        List<AddressResponse> addressResponseList = getAddressResponseList(addressList);
        List<OrderInfoResponse> orderInfoResponseList = getOrderInfoResponseList(orderList);
        List<VehicleResponse> vehicleResponseList = getVehicleResponseList(vehicleList);

        TransporterInfoResponse transporterInfoResponse = transporter.toTransporterInfoResponse(
                addressResponseList,
                orderInfoResponseList,
                vehicleResponseList
        );

        return ResponseEntity.ok().body(transporterInfoResponse);
    }

    private List<OrderInfoResponse> getOrderInfoResponseList(List<Order> orderList) {
        List<OrderInfoResponse> orderInfoResponseList = new ArrayList<>();

        orderList.forEach(
                order -> {
                    List<StatusResponse> statusResponseList = getStatusResponseList(order);

                    List<ProductResponse> productResponseList = getProductResponseList(order);

                    orderInfoResponseList.add(order.toOrderInfoResponse(statusResponseList, productResponseList));
                }
        );
        return orderInfoResponseList;
    }

    private List<StatusResponse> getStatusResponseList(Order order) {
        List<StatusResponse> statusResponseList = new ArrayList<>();
        orderStatusService.getAllByOrderId(order.getId()).forEach(orderStatus -> {
                    statusResponseList.add(
                            orderStatus.toStatusResponse()
                    );
                }
        );
        return statusResponseList;
    }

    private List<ProductResponse> getProductResponseList(Order order) {
        List<ProductResponse> productResponseList = new ArrayList<>();
        orderProductService.getAllOrderProductsByOrderId(order.getId()).forEach(orderProduct -> {
            productResponseList.add(
                    orderProduct.toProductResponse()
            );
        });
        return productResponseList;
    }

    public ResponseEntity<MessageResponse> confirmPickup(Long orderId) {
        Order order = orderService.getOrderById(orderId);
        Customer customer = order.getCustomer();
        orderStatusManagerService.pickedUp(order);
        String verificationCode = verificationCodeService.generateDeliveryVerificationCode();
        order.setDeliveryVerificationCode(encoder.encode(verificationCode));
        order.setDeliveryVerificationCodeTimestamp(new Date());

        orderService.saveOrder(order);


        String emailContent = "<html>" +
                "<head>" +
                "<style>" +
                ".container { width: 60%; margin: 0 auto; text-align: center; border: 2px solid #ddd; border-radius: 10px; padding: 10px; }" +
                ".code { font-weight: bold; font-size: 24px; }" +
                ".codeA { font-weight: bold; font-size: 16px; }" +
                "p { font-size: 16px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<p>Hola,<span class='codeA'>" + customer.getName() + "</span></p>" +
                "<p>Tu código de verificación de entrega del pedido : " + order.getId() + " es: </p>" +
                "<div class='code'>" + verificationCode + "</div>" +
                "<p>Entrega este código de verificación al transportador para poder recibir tu pedido.</p>" +
                "</div>" +
                "</body>" +
                "</html>";
        emailService.sendEmail(customer.getEmail(), "Código de entrega de pedido", emailContent);
        notificationService.notifyPickupToCustomer(order);
        return ResponseEntity.ok(new MessageResponse("Se ha notificado al comprador"));
    }

    public ResponseEntity<MessageResponse> confirmDelivery(Long orderId, String verificationCode) {
        Order order = orderService.getOrderById(orderId);
        boolean match = verificationCodeService.verificationCodeMatches(order, verificationCode);

        if (match) {
            orderStatusManagerService.delivered(order);
            return ResponseEntity.ok(new MessageResponse("Código de verificación válido. Pedido entregado"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Código de verificación inválido"));
        }
    }


}
