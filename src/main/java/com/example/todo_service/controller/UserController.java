package com.example.todo_service.controller;

import com.example.todo_service.model.User;
import com.example.todo_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        // Вызов сервиса для регистрации пользователя
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<User> updateRole(@PathVariable Long userId, @RequestParam boolean isAdmin) {
        // Вызов сервиса для изменения роли пользователя
        User updatedUser = userService.updateRole(userId, isAdmin);
        return ResponseEntity.ok(updatedUser);
    }
}
