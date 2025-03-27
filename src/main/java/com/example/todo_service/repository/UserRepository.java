package com.example.todo_service.repository;

import com.example.todo_service.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<User> findByLogin(String login) {
        String sql = "SELECT * FROM person WHERE login = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{login}, (rs, rowNum) ->
                    new User(
                            rs.getLong("id"),
                            rs.getString("login"),
                            rs.getString("password"),
                            rs.getBoolean("isadmin")
                    ));
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public User save(User user) {
        if (user.getId() == null) {
            String sql = "INSERT INTO person (login, password, isadmin) VALUES (?, ?, ?) RETURNING id";
            Long id = jdbcTemplate.queryForObject(sql, Long.class,
                    user.getLogin(),
                    user.getPassword(),
                    user.isAdmin());
            user.setId(id);
        } else {
            String sql = "UPDATE person SET login = ?, password = ?, isadmin = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    user.getLogin(),
                    user.getPassword(),
                    user.isAdmin(),
                    user.getId());
        }
        return user;
    }
    public Optional<User> findById(Long userId) {
        String sql = "SELECT * FROM person WHERE id = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{userId}, (rs, rowNum) ->
                    new User(
                            rs.getLong("id"),
                            rs.getString("login"),
                            rs.getString("password"),
                            rs.getBoolean("isadmin")
                    ));
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM person";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new User(
                        rs.getLong("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getBoolean("isadmin")
                )
        );
    }



    // Другие методы репозитория...
}