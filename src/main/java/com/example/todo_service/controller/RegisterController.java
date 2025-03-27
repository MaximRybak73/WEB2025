package com.example.todo_service.controller;

import com.example.todo_service.UserAlreadyExistsException;
import com.example.todo_service.dto.UserRegistrationDto;
import com.example.todo_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RegisterController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register (@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "login";
        }

        try {
            userService.registerUser(userDto);
            redirectAttributes.addFlashAttribute("success", "Registration successful!");
            return "redirect:/";
        } catch (UserAlreadyExistsException e) {
            result.rejectValue("login", "user.exists", e.getMessage());
            return "login";
        }
    }

}