package com.example.todo_service.repository;

import com.example.todo_service.model.Card;
import com.example.todo_service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class CardRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Card> findByExecutor(Long execution) {
        String sql = "SELECT * FROM card WHERE executor_id = ?";
        return jdbcTemplate.query(sql, new Object[]{execution}, (rs, rowNum) ->
                new Card(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getDate("creation_date"),
                        rs.getLong("executor_id") // Возможно, здесь должно быть rs.getString("login")
                ));
    }
    public List<Card> findByExecutor(String execution) {
        String sql = "SELECT card.id, card.status, card.name, card.description, card.creation_date, person.login FROM card LEFT JOIN person ON person.id = card.executor_id WHERE person.login = ? or (card.executor_id is NULL) ;";
        return jdbcTemplate.query(sql, new Object[]{execution}, (rs, rowNum) ->
                new Card(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getDate("creation_date"),
                        rs.getLong("login") // Возможно, здесь должно быть rs.getString("login")
                ));
    }

    public Card save(Card card) {
        if (card.getId() == null) {
            String sql = "INSERT INTO card ( status, name, description, creation_date, executor_id) VALUES ( ?, ?, ?, ?, ?) RETURNING id";
            Long id = jdbcTemplate.queryForObject(sql, Long.class,
                    card.getStatus(),
                    card.getName(),
                    card.getDescription(),
                    card.getCreationDate(),
                    card.getExecutor_id());
            card.setId(id);
        } else {
            String sql = "UPDATE card SET status = ?, name = ?, description = ?, creation_date = ?, executor_id = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    card.getStatus(),
                    card.getName(),
                    card.getDescription(),
                    card.getCreationDate(),
                    card.getExecutor_id(),
                    card.getId());
        }
        return card;
    }
    public Optional<Card> findById(Long cardId) {
        String sql = "SELECT * FROM card WHERE id = ?";
        try {
            Card card = jdbcTemplate.queryForObject(sql, new Object[]{cardId}, (rs, rowNum) ->
                    new Card(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("status"),
                            rs.getDate("creation_date"),
                            rs.getLong("executor_id")
                    ));
            return Optional.ofNullable(card);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Card> getAll() {
        String sql = "SELECT card.id, card.status, card.name, card.description, card.creation_date, card.executor_id FROM card LEFT JOIN person ON person.id = card.executor_id;";
        return jdbcTemplate.query(sql, new Object[]{}, (rs, rowNum) ->
                new Card(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getDate("creation_date"),
                        rs.getLong("executor_id") // Возможно, здесь должно быть rs.getString("login")
                ));
    }

    // Другие методы репозитория...
}