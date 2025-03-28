package com.example.todo_service.service;

import com.example.todo_service.InvalidCredentialsException;
import com.example.todo_service.UserAlreadyExistsException;
import com.example.todo_service.model.User;
import com.example.todo_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    public User authenticateUser(User loginDto) {
        // Находим пользователя по логину
        Optional<User> userOptional = userRepository.findByLogin(loginDto.getLogin());

        if (userOptional.isEmpty()) {
            throw new InvalidCredentialsException("Invalid login or password");
        }

        User user = userOptional.get();

        // Проверяем совпадение паролей
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid login or password");
        }

        return user;
    }

    public void registerUser(User registrationDto) {
        if (userRepository.findByLogin(registrationDto.getLogin()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = new User();
        user.setLogin(registrationDto.getLogin());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setAdmin(false);

        userRepository.save(user);
    }
    public User updateRole(Long userId, boolean isAdmin) {
        // Получение пользователя по ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Изменение роли пользователя
        user.setAdmin(isAdmin);
        return userRepository.save(user); // Сохранение изменений в базе данных
    }

    public List<User> getAllUsers(){
        return userRepository.getAllUsers();
    }
    public void registerAdmin(User registrationDto) {
        User user = new User();
        user.setLogin(registrationDto.getLogin());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setAdmin(true);
        userRepository.save(user);
    }
}
