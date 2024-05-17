package com.example.springbootjwtauthentication.service.implementations;

import com.example.springbootjwtauthentication.model.User;
import com.example.springbootjwtauthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con ID: " + username));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con email: " + email));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado en UserService, Id: " + id));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public void updatePasswordById(String password, Long id) {
        userRepository.updatePasswordById(password, id.toString())
                .orElseThrow(() -> new RuntimeException("No se pudo actualizar el usuario con ID: " + id));
    }

    public void resetVerificationCodeById(Long id) {
        userRepository.resetVerificationCodeById(id)
                .orElseThrow(() -> new RuntimeException("No se pudo reestablecer el código de verificación del usuario con ID: " + id));
    }
}
