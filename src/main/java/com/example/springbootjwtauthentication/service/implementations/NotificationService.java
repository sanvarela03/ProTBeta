package com.example.springbootjwtauthentication.service.implementations;


import com.example.springbootjwtauthentication.model.Customer;
import com.example.springbootjwtauthentication.model.Order;
import com.example.springbootjwtauthentication.model.Producer;
import com.example.springbootjwtauthentication.model.Transporter;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.google.firebase.messaging.*;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    private FirebaseMessaging firebaseMessaging;

    @Async
    public void notifyCustomer(Order order, Customer customer) {
        log.info("SIMULACRO DE NOTIFICACION AL COMPRADOR ...");
        //TODO
    }

    @Async
    public void notifyPickupToCustomer(Order order) {
        Customer customer = order.getCustomer();
        log.info("SIMULACRO DE NOTIFICACION DE RECOLECCIÓN AL COMPRADOR: " + customer.getName());
        //TODO
    }

    /**
     * Notifica al transportador que un nuevo pedido esta buscando transportador
     */
    @Async
    public void notifyTransporter(Order order, Transporter transporter) throws FirebaseMessagingException {

        log.info("SIMULACIÓN DE NOTIFICACIÓN CON FIREBASE ...");

//        Notification notification = Notification.builder()
//                .setTitle("Solicitud de  transporte de pedido")
//                .setBody(order.getCustomer().getName() + " ha realizado un pedido")
//                .build();
//
//        Message msg = Message.builder()
//                .setToken(transporter.getFirebaseToken())
//                .setNotification(notification)
//                .setAndroidConfig(AndroidConfig.builder()
//                        .setNotification(AndroidNotification.builder()
//                                .setSound("default")
//                                .setClickAction("YOUR_CLICK_ACTION")
//                                .setDefaultLightSettings(true)
//                                .setColor("#fca103")
//                                .setBodyLocalizationKey("")
//                                .build())
//                        .build())
//                .build();
//        firebaseMessaging.send(msg);
    }

    /**
     * Notifica al productor que el hay un nuevo cliente realizando una solicitud del pedido
     */
    @Async
    public void notifyProducer(Order order) throws FirebaseMessagingException {
        Customer customer = order.getCustomer();
        Producer producer = order.getProducer();

        String msgTittle = "Nueva solicitud de pedido";
        String msgBody = "Hola " + producer.getName() + ", " + customer.getName()+ " " + customer.getLastName() + " ha realizado un pedido, revisa los detalles del pedido y responde la solicitud.";


        log.info("SIMULACIÓN DE NOTIFICACIÓN CON FIREBASE ({}) ...", Thread.currentThread().getName());

        Notification notification = Notification.builder()
                .setTitle(msgTittle)
                .setBody(msgBody)
                .build();


        // String registrationToken = "d1N9j1msRraUfigR5m9hoT:APA91bHx1IYT5ia2isW0GHeuI-RmDRaYLaiyLkEmMPJ0PCbfdWYRM1cdPLqSmCu-yosuT-JFZtkbGCBH-YSHxj6q3E0O0A52oKlxOHBoG-r6f0G4cF-8Gxxo-K-6Zn5lFI0eVSnAkr28";
        String registrationToken = producer.getFirebaseToken();
        Message msg = Message.builder()
                .setToken(registrationToken)
                .setNotification(notification)
                .setAndroidConfig(AndroidConfig.builder()
                        .setNotification(AndroidNotification.builder()
                                .setSound("default")
                                .setClickAction("YOUR_CLICK_ACTION")
                                .setDefaultLightSettings(true)
                                .setColor("#fca103")
                                .setBodyLocalizationKey("")
                                .build())
                        .build())
                .build();

        String id = firebaseMessaging.send(msg);
    }
}
