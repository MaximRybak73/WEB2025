package com.example.jdbc.repository.mappers

import com.example.jdbc.repository.models.User
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.sql.SQLException

/**
 * Маппер моделей пользователей
 */
@Component
class UsersMapper : RowMapper<User?> {

    /**
     * Указывается поле и тип данных, которые нужно достать из БД по запросу
     */
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): User {
        return User(
            rs.getInt("id")
        )
    }
}