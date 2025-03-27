package com.example.todo_service.service;

import com.example.todo_service.model.Card;
import com.example.todo_service.model.User;
import com.example.todo_service.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    public Card createCard(Card card) {
        // Сохранение новой карточки в базу данных
        return cardRepository.save(card);
    }

    public Card updateStatus(Long cardId, String status) {
        // Получение карточки по ID
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        // Изменение статуса карточки
        card.setStatus(status);
        return cardRepository.save(card); // Сохранение изменений в базе данных
    }

    public Card assignExecutor(Long cardId, User executor) {
        // Получение карточки по ID
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        // Назначение исполнителя
        card.setExecutor(executor);
        return cardRepository.save(card); // Сохранение изменений в базе данных
    }
}