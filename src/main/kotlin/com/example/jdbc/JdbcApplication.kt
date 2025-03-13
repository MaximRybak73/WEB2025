package com.example.jdbc

import com.example.jdbc.models.Person
import com.example.jdbc.repository.interfaces.ProfileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.server.Session
import org.springframework.util.DigestUtils
import java.util.*

/**
 * ЗАДАНИЕ:
 * 1. Добавить в pom.xml стартеры для jdbc, postgres и сессии. Место для добавления обозначено <!--начало--> и
 * <!--конец-->
 * 2. Создать базу данных с таблицами, описанными в диаграмме
 * 3. Настроить конфиг файл resources.application.yml для подключения к БД
 * 4. Доработать написанный код для получения id пользователя по логину и паролю. При этом необходимо сделать сохранение
 * пользователя в сессию. При запуске мейна создается пустой пользователь, который сохраняется в сессию. После этого он
 * должен насыщаться данными, взаимодействие с моделью пользователя через сессию.
 * https://javastudy.ru/spring-mvc/save-object-in-session/
 * https://ru.stackoverflow.com/questions/683597/session-%D0%B2-spring-mvc
 * 5. Добавить модели, интерфейсы и реализации для получения тикетов из базы
 *
 * Обращайте внимание на кодстайл. Для проверки можно установить специальные модули. Обращайте внимание на названия
 * переменных. Обращайте внимание на то, как разделяете логику на классы.
 *
 * Так как никакой логики кроме получения данных из БД нету, этот файл является контроллером. Подключайте сервисы к нему
 * и описывайте методы взаимодействия здесь.
 */

@SpringBootApplication
class JdbcApplication

@Autowired
var profileRepository: ProfileService? = null

var userId: Int? = null

/**
 * Запускает приложение. На старте необходимо добавить создание пустого персона, сделать метод
 */
fun main(args: Array<String>) {
    runApplication<JdbcApplication>(*args)

    val incomingPerson = Person() //Создаем пустого персона и сохраняем в сессию
    getSession().setAttribute("person", incomingPerson)

    login() //Заполняем персона данными (как будто он ввел логин с паролем и прожал авторизацию)

    println(userId) //Выводим его id из БД
}

/**
 * Залогинить пользователя
 *
 * @param person заполненный пользователь
 */
fun login() {
    person = getSession().getAttribute("person") //Получение персона из сессии. Типа того, поменяйте если сделаете иначе

    userId = profileRepository?.getUser(
        person.login, person.password
    )
}

/**
 * Заглушка авторизации пользователя. В реальности это будет метод, который возвращает с фронта то, что пользователь
 * указал в логине и пароле. Здесь нужно записать данные пользователя, который сохранен в БД для вызова. Если их
 * несколько, сделайте свич кейс.
 */
fun authorise(person: Person){
    person.login = ""
    person.password = ""
}
