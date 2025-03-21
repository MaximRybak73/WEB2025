package com.example.todo_service.controller;

import com.example.todo_service.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class TaskViewController {
    private final CardService cardService;

    @GetMapping("/cards")
    public String getAllTasks(Model model) {
        model.addAttribute("cards",  cardService.getAllcards());
        return "cards";
    }

    @GetMapping("/cards/{id}/assign")
    public String assignTaskForm(@PathVariable Long id, Model model) {
        model.addAttribute("taskId", id);
        return "assign-task";
    }

    @GetMapping("/tasks/{id}/status")
    public String changeStatusForm(@PathVariable Long id, Model model) {
        model.addAttribute("taskId", id);
        return "change-status";
    }
}