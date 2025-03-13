package com.example.jdbc.repository.interfaces

/**
 * Интерфейс взаимодействия с базой пользователей
 */
interface ProfileService {
    /**
     * Получение пользователя
     *
     * @param login логин
     * @param password пароль
     * @return -1 если пользователь не найден, id если найден
     */
    fun getUser(login: String?, password: String?): Int
}