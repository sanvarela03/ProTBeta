package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.*;
import com.example.springbootjwtauthentication.model.Enum.ERole;
import com.example.springbootjwtauthentication.model.Enum.EState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderStateRepositoryTest {

    @Autowired
    protected OrderRepository orderRepository;
    @Autowired
    protected OrderStateRepository orderStateRepository;
    @Autowired
    protected ProducerRepository producerRepository;
    @Autowired
    protected CustomerRepository customerRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected RoleRepository roleRepository;
    @Autowired
    protected StateRepository stateRepository;


    protected Producer producer;
    protected Customer customer;

    @BeforeEach
    void setUp() {
        producer = Producer
                .builder()
                .username("svarela03")
                .name("Santiago")
                .lastName("Varela")
                .email("svarela03@uan.edu.co")
                .password(passwordEncoder.encode("12345"))
                .build();
        Role roleUser = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Rol no encontrado: " + ERole.ROLE_USER));
        Role roleProducer = roleRepository.findByName(ERole.ROLE_PRODUCER).orElseThrow(() -> new RuntimeException("Rol no encontrado: " + ERole.ROLE_PRODUCER));
        producer.setRoleEntities(Set.of(roleUser, roleProducer));
        producerRepository.save(producer);

        customer = Customer
                .builder()
                .username("sanvarela03")
                .name("Santiago")
                .lastName("Varela Daza")
                .email("santi98vdip@gmail.com")
                .password(passwordEncoder.encode("12345"))
                .build();
        Role roleCustomer = roleRepository.findByName(ERole.ROLE_CUSTOMER).orElseThrow(() -> new RuntimeException("Rol no encontrado: " + ERole.ROLE_CUSTOMER));
        customer.setRoleEntities(Set.of(roleUser, roleCustomer));
        customerRepository.save(customer);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testOrderAdd() {
        System.out.printf("Producer : < %s > %n", producer);
        System.out.printf("Customer : < %s > %n", customer);

        Order order = new Order(customer, producer);
        orderRepository.save(order);
        System.out.printf("Order : < %s > %n", order);

        State state =
                stateRepository
                        .findByName(EState.CREATED)
                        .orElseThrow(() -> new RuntimeException("Estado no encontrado: " + EState.CREATED));
        System.out.printf("State : < %s > %n", state);
        OrderState orderState = new OrderState(order, state);
        System.out.printf("Before_OrderState : < %s > %n", orderState);
        orderStateRepository.save(orderState);
        System.out.printf("After_OrderState : < %s > %n", orderState);
    }
}