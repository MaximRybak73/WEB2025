package com.example.todo_service.controller;

import com.example.todo_service.UserAlreadyExistsException;
import com.example.todo_service.model.Card;
import com.example.todo_service.model.User;
import com.example.todo_service.service.CardService;
import com.example.todo_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.time.LocalDate;

@Controller
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private CardService cardService;


    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/api/users/{userId}/role")
    public String updateRole(
            @PathVariable Long userId,
            @RequestParam boolean isAdmin,
            RedirectAttributes redirectAttributes) {
        userService.updateRole(userId, isAdmin);
        redirectAttributes.addFlashAttribute("success", "Роль успешно обновлена");
        return "redirect:/admin/users"; // Перенаправление обратно на страницу управления пользователями
    }

    @PostMapping("/auth/register")
    public String register (@ModelAttribute("user") @Valid User userDto,
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

    //CardContoll
    @GetMapping("/tasks")
    public String task(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Проверяем, есть ли у пользователя роль ADMIN
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            // Если админ - загружаем все карточки
            model.addAttribute("cards", cardService.getAll());
        } else {
            // Если не админ - только карточки текущего исполнителя
            model.addAttribute("cards", cardService.getAllByExecutor(username));
        }

        model.addAttribute("allUsers", userService.getAllUsers());
        return "task";
    }

    @GetMapping("/tasks/{id}/assign")
    public String showAssignForm(@PathVariable Long id, Model model) {
        model.addAttribute("card", cardService.getCardById(id));
        model.addAttribute("allUsers", userService.getAllUsers());
        return "assign-form";
    }

    @PostMapping("/tasks/{id}/assign")
    public String assignExecutor(@PathVariable Long id,
                                 @RequestParam Long executorId) {
        cardService.assignExecutor(id, executorId);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{id}/status")
    public String showStatusForm(@PathVariable Long id, Model model) {
        model.addAttribute("card", cardService.getCardById(id));
        return "status-form";
    }

    @PostMapping("/tasks/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam String status) {
        cardService.updateStatus(id, status);
        return "redirect:/tasks";
    }

    @PostMapping("/tasks")
    public String createCard(@ModelAttribute Card card) {
        // Если дата не указана, устанавливаем текущую дату
        if (card.getCreationDate() == null) {
            card.setCreationDate(Date.valueOf(LocalDate.now()));
        }

        // Если статус не указан, устанавливаем "OPEN"
        if (card.getStatus() == null || card.getStatus().isEmpty()) {
            card.setStatus("OPEN");
        }

        cardService.createCard(card);
        return "redirect:/tasks";
    }

    //--AuthContoller

    @GetMapping("/auth/login")
    public String login() {
        return "login";
    }

    @PostMapping("/auth/login")
    public String login(@RequestBody User loginDto) {
        User user = userService.authenticateUser(loginDto);
        return "/";
    }
    @GetMapping("/auth/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    //---AdminController

    @GetMapping("/admin/users")
    public String userManagement(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }
}
