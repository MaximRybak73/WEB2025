package com.example.jdbc.repository.impls

import com.example.jdbc.repository.interfaces.ProfileService
import com.example.jdbc.repository.mappers.UsersMapper
import com.example.jdbc.repository.models.User
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Реализация интерфейса ProfileRepository
 */
@Repository
class ProfileServiceImpl(
    usersMapper: UsersMapper,
    jdbcTemplate: NamedParameterJdbcTemplate
) : ProfileService {
    private val profileMapper: UsersMapper
    private val jdbcTemplate: NamedParameterJdbcTemplate

    init {
        profileMapper = usersMapper
        this.jdbcTemplate = jdbcTemplate
    }

    override fun getUser(login: String?, password: String?): Int {
        val params = MapSqlParameterSource()

        params.addValue("log", login)
        params.addValue("pass", password)
        val result: Optional<User?> = jdbcTemplate.query(
            SQL_GET_PROFILE_BY_ID,
            params,
            profileMapper
        ).stream()
            .findFirst()
        return if (result.isEmpty) -1 else result.get().id()
    }

    /**
     * Запросы к БД
     */
    companion object {
        private const val SQL_GET_PROFILE_BY_ID = "select id from users where login = :log and password = :pass"
        private const val SQL_INSERT_PROFILE = "insert into users (login, password) values (:login, :password)"
    }
}