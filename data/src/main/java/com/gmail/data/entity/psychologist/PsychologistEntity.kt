package com.gmail.data.entity

import com.google.firebase.firestore.GeoPoint

data class PsychologistEntity(
    val id: String,
    val avatar: String,                    // Аватарка
    val name: String,                        // Имя
    val surname: String,                    // Фамилия
    val patronymic: String?,                // Отчество
    val profile: String,                    // Информация о том, чем ты можешь помочь человеку
    val country: String,                    // Страна
    val city: String,                        // Город
    val geoPoint: GeoPoint,
    val contacts: List<Contact>,                // Контакты
    val education: List<Education>,            // Образование
    val specialization: Specialization,        //Специализация
    val experience: Int,                       // Опыт работы в количестве лет
    val rating: Float,                        // Рейтинг
    val numberOfVotes: Int                        // Количество голосов
)

data class Contact(
    val titleContact: String,            // Телефон, почта и т.д.
    val contact: String                    // Сам контакт
)

data class Education(
    val university: String,                // Учреждение образования
    val faculty: String,                // Факультет
    val specialization: String,            // Специальность
    val yearOfGraduation: Int            // Год окончания
)

data class Specialization(
    val profession: String,                // Специальность
    val specialization: List<String>,    // Специализация
)


