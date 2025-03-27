package com.example.todo_service.controller;

import com.example.todo_service.model.Card;
import com.example.todo_service.model.User;
import com.example.todo_service.service.CardService;
import com.example.todo_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    @Autowired
    private CardService cardService;
    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<Card> createCard(@RequestBody Card card) {
        // Вызов сервиса для создания карточки
        Card createdCard = cardService.createCard(card);
        return ResponseEntity.ok(createdCard);
    }

    @PutMapping("/{cardId}/status")
    public ResponseEntity<Card> updateStatus(@PathVariable Long cardId, @RequestParam String status) {
        // Вызов сервиса для изменения статуса карточки
        Card updatedCard = cardService.updateStatus(cardId, status);
        return ResponseEntity.ok(updatedCard);
    }

    @PutMapping("/{cardId}/executor")
    public ResponseEntity<Card> assignExecutor(@PathVariable Long cardId, @RequestParam Long executorId) {
        // Вызов сервиса для назначения исполнителя
        User executor = userService.getUserById(executorId); // Предположим, что есть метод для получения пользователя по ID
        Card updatedCard = cardService.assignExecutor(cardId, executor);
        return ResponseEntity.ok(updatedCard);
    }
}