package com.example.todo_service.controller;

import com.example.todo_service.UserAlreadyExistsException;
import com.example.todo_service.dto.UserRegistrationDto;
import com.example.todo_service.model.User;
import com.example.todo_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
//    @PostMapping("/register")
//    public ResponseEntity<User> registerUser(@RequestBody User user) {
//        // Вызов сервиса для регистрации пользователя
//        User registeredUser = userService.registerUser(user);
//        return ResponseEntity.ok(registeredUser);
//    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<User> updateRole(@PathVariable Long userId, @RequestParam boolean isAdmin) {
        // Вызов сервиса для изменения роли пользователя
        User updatedUser = userService.updateRole(userId, isAdmin);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("registrationDto") @Valid UserRegistrationDto registrationDto,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "register";
        }

        try {
            userService.registerUser(registrationDto);
            redirectAttributes.addFlashAttribute("success", "Registration successful!");
            return "redirect:/login";
        } catch (UserAlreadyExistsException e) {
            result.rejectValue("login", "user.exists", e.getMessage());
            return "register";
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    // Авторизация
//    @PostMapping("/login")
//    public ResponseEntity<String> loginUser(@RequestBody User user) {
//        String token = userService.loginUser(user.getLogin(), user.getPassword());
//        return ResponseEntity.ok(token);
//    }
}
