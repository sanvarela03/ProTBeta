package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.Enum.ERole;
import com.example.springbootjwtauthentication.model.Role;
import com.example.springbootjwtauthentication.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public Role getRoleByName(ERole name) {
        return repository.findByName(name)
                .orElseThrow(() -> new RuntimeException("No se encontró el rol con nombre: " + name));
    }
}
