package com.example.todo_service.controller;

import com.example.todo_service.dto.UserLoginDto;
import com.example.todo_service.dto.UserRegistrationDto;
import com.example.todo_service.model.User;
import com.example.todo_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody UserLoginDto loginDto) {
        User user = userService.authenticateUser(loginDto);
        return "";
    }
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }
}