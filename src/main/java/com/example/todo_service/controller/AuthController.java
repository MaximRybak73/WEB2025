package com.example.todo_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Просто возвращаем шаблон, без редиректов
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register"; // Просто возвращаем шаблон
    }
}
