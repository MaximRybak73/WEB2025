package com.example.todo_service.service;

import com.example.todo_service.model.User;
import com.example.todo_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void makeAdmin(String username) {
        User user = userRepository.findByUsername(username);
        user.setAdmin(true);
        userRepository.save(user);
    }
}