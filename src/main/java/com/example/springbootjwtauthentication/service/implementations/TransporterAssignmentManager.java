package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.Order;
import com.example.springbootjwtauthentication.model.Transporter;
import com.example.springbootjwtauthentication.payload.request.TransporterAnswerRequest;
import com.example.springbootjwtauthentication.service.interfaces.TransporterAnswerListener;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.HashMap;

@Component
@Slf4j
public class TransporterAssignmentManager {
    private HashMap<Long, TransporterAnswerListener> listeners = new HashMap<>();

    public void subscribe(TransporterAnswerListener listener, Order order) {

        boolean valid = !listeners.containsKey(order.getId()) && !listeners.containsValue(listener);
        if (valid) {
            this.listeners.put(order.getId(), listener);
        } else {
            log.info("La clave : {} y el valor : {} no son validas", order.getId(), listener);
        }
    }

    public void unsubscribe(TransporterAnswerListener listener, Order order) {
        this.listeners.remove(order.getId(), listener);
    }

    public void notify(TransporterAnswerRequest data, Transporter transporter) throws FirebaseMessagingException, ParseException {
        TransporterAnswerListener listener = listeners.get(data.getOrderId());

        //TODO: Si existe ese pedido entonces continuar
        if (listeners.containsValue(listener)) {
            listener.update(this, transporter, data);
        } else {
            log.info("No se encontró el oyente asociado a este pedido");
        }
    }
}
