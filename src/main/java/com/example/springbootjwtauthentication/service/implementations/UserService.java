package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.User;
import com.example.springbootjwtauthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con ID: " + username));
    }

    public User saveUser(User user) {
        return repository.save(user);
    }

    public User getUserByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con email: " + email));
    }

    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    public void updatePasswordById(String password, Long id) {
        repository.updatePasswordById(password, id.toString())
                .orElseThrow(() -> new RuntimeException("No se pudo actualizar el usuario con ID: " + id));
    }

    public void resetVerificationCodeById(Long id) {
        repository.resetVerificationCodeById(id)
                .orElseThrow(() -> new RuntimeException("No se pudo reestablecer el código de verificación del usuario con ID: " + id));
    }
}
