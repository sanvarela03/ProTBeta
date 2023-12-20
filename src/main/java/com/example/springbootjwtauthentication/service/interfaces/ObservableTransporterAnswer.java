package com.example.springbootjwtauthentication.service.interfaces;

import com.example.springbootjwtauthentication.model.Order;
import com.example.springbootjwtauthentication.model.Transporter;
import com.example.springbootjwtauthentication.payload.request.TransporterAnswerRequest;
import com.google.firebase.messaging.FirebaseMessagingException;

public interface ObservableTransporterAnswer {
    void subscribe(TransporterAnswerListener listener, Order order);

    void unsubscribe(TransporterAnswerListener listener, Order order);

    void notifySubscribers();

    void notify(TransporterAnswerListener observer, TransporterAnswerRequest data, Transporter transporter) throws FirebaseMessagingException;
}
