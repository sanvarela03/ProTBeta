package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.Customer;
import com.example.springbootjwtauthentication.model.Enum.ERole;
import com.example.springbootjwtauthentication.model.Producer;
import com.example.springbootjwtauthentication.model.Product;
import com.example.springbootjwtauthentication.model.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {
    @Autowired
    protected ProducerRepository producerRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected RoleRepository roleRepository;
    @Autowired
    private ProductRepository productRepository;
    protected Producer producer;

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
        Product product1 = new Product("Queso de cabra", 3, "Un queso de cabra sep", 12000.0, true, producer);
        Product product2 = new Product("Cubeta de Huevos", 4, "Un queso de cabra sep", 7000.0, true, producer);

        productRepository.saveAll(List.of(product1, product2));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testFindByProducer() {
        List<Product> products = productRepository.findByProducer_Id(producer.getId())
                .orElseThrow(() -> new RuntimeException("No se encontro el productor : " + producer.getName()));

        System.out.println("Products: " + products.toString());
        System.out.println("Producer: " + products.get(0).getProducer());


    }
}