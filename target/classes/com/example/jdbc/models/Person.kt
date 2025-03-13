package com.example.jdbc.models

/**
 * Модель пользователя. Того, который использует пиложение, а не записан в базу данных. Может дополняться полями для
 * ссылки на карзину покупок, или что еще будет нужно в процессе работы с ним.
 */
class Person {
    var login: String? = null
    var password: String? = null
    var id: Int? = null
}