package com.example.todo_service.controller;

import com.example.todo_service.model.Card;
import com.example.todo_service.model.User;
import com.example.todo_service.service.CardService;
import com.example.todo_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class CardController {
    @Autowired
    private CardService cardService;
    @Autowired
    private UserService userService;
    @GetMapping("")
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

    @GetMapping("/{id}/assign")
    public String showAssignForm(@PathVariable Long id, Model model) {
        model.addAttribute("card", cardService.getCardById(id));
        model.addAttribute("allUsers", userService.getAllUsers());
        return "assign-form";
    }

    @PostMapping("/{id}/assign")
    public String assignExecutor(@PathVariable Long id,
                                 @RequestParam Long executorId) {
        cardService.assignExecutor(id, executorId);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/status")
    public String showStatusForm(@PathVariable Long id, Model model) {
        model.addAttribute("card", cardService.getCardById(id));
        return "status-form";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam String status) {
        cardService.updateStatus(id, status);
        return "redirect:/tasks";
    }

    @PostMapping
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
}