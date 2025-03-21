package com.example.todo_service.service;

import com.example.todo_service.model.Card;
import com.example.todo_service.model.CardStatus;
import com.example.todo_service.model.User;
import com.example.todo_service.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    public Card createCard(Card Card) {
        return cardRepository.save(Card);
    }

    public Card updatecardStatus(Long cardId, CardStatus status) {
        Card Card = cardRepository.findById(cardId).orElseThrow();
        Card.setStatus(status);
        return cardRepository.save(Card);
    }

    public Card assigncard(Long cardId, Long assignee) {
        Card Card = cardRepository.findById(cardId).orElseThrow();
        Card.setAssignee(assignee);
        return cardRepository.save(Card);
    }

    public List<Card> getAllcards() {
        return cardRepository.findAll();
    }
}