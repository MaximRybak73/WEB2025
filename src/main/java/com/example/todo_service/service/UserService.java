package com.example.todo_service.service;

import com.example.todo_service.model.User;
import com.example.todo_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User registerUser(User user) {
        // Проверка, существует ли пользователь с таким логином
        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        // Сохранение пользователя в базу данных
        return userRepository.save(user);
    }

    public User updateRole(Long userId, boolean isAdmin) {
        // Получение пользователя по ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Изменение роли пользователя
        user.setAdmin(isAdmin);
        return userRepository.save(user); // Сохранение изменений в базе данных
    }
}