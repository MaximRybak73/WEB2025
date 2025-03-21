package com.example.todo_service.repository;

import com.example.todo_service.model.Card;
import com.example.todo_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByExecutor(User executor);
}
