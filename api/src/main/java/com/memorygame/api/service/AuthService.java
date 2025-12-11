package com.memorygame.api.service;

import com.memorygame.api.model.User;
import com.memorygame.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public boolean login(String username, String password) {
        return userRepository.findByUsername(username)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

    public boolean register(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            return false;
        }
        
        User user = new User(username, password);
        userRepository.save(user);
        return true;
    }
}

