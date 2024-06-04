package com.example.springbootjwtauthentication.service.interfaces;

import com.example.springbootjwtauthentication.model.Order;
import com.example.springbootjwtauthentication.model.Transporter;
import com.example.springbootjwtauthentication.payload.request.TransporterAnswerRequest;
import com.example.springbootjwtauthentication.service.implementations.TransporterAssignmentManager;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.scheduling.annotation.Async;

import java.text.ParseException;
import java.util.List;

public interface TransporterAnswerListener {
    void update(TransporterAssignmentManager manager, Transporter transporter, TransporterAnswerRequest data) throws FirebaseMessagingException, ParseException;

    void startJob(TransporterAssignmentManager manager, List<Transporter> candidateTransportersList, Order order) throws FirebaseMessagingException;
}
