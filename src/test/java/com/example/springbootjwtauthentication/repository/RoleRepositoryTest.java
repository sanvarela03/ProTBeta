package com.example.springbootjwtauthentication.repository;

import com.example.springbootjwtauthentication.model.Enum.ERole;
import com.example.springbootjwtauthentication.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;
import java.util.Base64;

@SpringBootTest
class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Test
    public void findByName(){
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: No encontrado."));
    }

    @Test
    public void genKey(){
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[64]; // 36 bytes * 8 = 288 bits, a little bit more than
        // the 256 required bits
        random.nextBytes(bytes);
        var encoder = Base64.getUrlEncoder().withoutPadding();

        var key = encoder.encodeToString(bytes);

        System.out.println("key = " + key);
    }
}