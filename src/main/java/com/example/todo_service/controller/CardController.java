package com.example.todo_service.controller;

import com.example.todo_service.model.Card;
import com.example.todo_service.model.CardStatus;
import com.example.todo_service.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping
    public Card createTask(@RequestBody Card task) {
        return cardService.createCard(task);
    }

    @PutMapping("/{id}/status")
    public Card updateTaskStatus(@PathVariable Long id, @RequestParam CardStatus status) {
        return cardService.updatecardStatus(id, status);
    }

    @PutMapping("/{id}/assign")
    public Card assignTask(@PathVariable Long id, @RequestParam Long assigneeId) {
        return cardService.assigncard(id, assigneeId);
    }

    @GetMapping
    public List<Card> getAllTasks() {
        return cardService.getAllcards();
    }
}