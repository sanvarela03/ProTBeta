package com.example.springbootjwtauthentication.service.interfaces;

import com.example.springbootjwtauthentication.model.Transporter;
import com.example.springbootjwtauthentication.payload.request.TransporterAnswerRequest;
import com.example.springbootjwtauthentication.service.implementations.TransporterAssignmentManager;
import com.google.firebase.messaging.FirebaseMessagingException;

import java.text.ParseException;

public interface TransporterAnswerListener {
    void update(TransporterAssignmentManager manager, Transporter transporter, TransporterAnswerRequest data) throws FirebaseMessagingException, ParseException;
}
