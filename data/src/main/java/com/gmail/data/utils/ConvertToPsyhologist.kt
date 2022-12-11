package com.gmail.data.utils

import com.gmail.data.entity.Contact
import com.gmail.data.entity.Education
import com.gmail.data.entity.PsychologistEntity
import com.gmail.data.entity.Specialization
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.GeoPoint

fun convertToPsychologistEntity(item: DocumentSnapshot): PsychologistEntity {
    val id = item.id
    val avatar = item.data?.get(KEY_AVATAR) as String
    val name = item.data?.get(KEY_NAME) as String
    val surname = item.data?.get(KEY_SURNAME) as String
    val patronymic = item.data?.get(KEY_PATRONYMIC) as String
    val profile = item.data?.get(KEY_PROFILE) as String
    val country = item.data?.get(KEY_COUNTRY) as String
    val city = item.data?.get(KEY_CITY) as String
    val geoPoint = item.data?.get(KEY_GEO_POINT) as GeoPoint
    val contacts = item.data?.get(KEY_CONTACTS) as List<Map<String, Any>>
    val resultContacts = convertContacts(contacts)
    val education = item.data?.get(KEY_EDUCATIONS) as List<Map<String, Any>>
    val resultEducation = convertEducation(education)
    val experience = item.data?.get(KEY_EXPERIENCE) as Number
    val rating = item.data?.get(KEY_RATING) as Number
    val numberOfVotes = item.data?.get(KEY_NUMBER_OF_VOTES) as Number
    val specialization = item.data?.get(KEY_SPECIALIZATION) as Map<String, Any>
    val resultSpecial = convertSpecial(specialization)
    return PsychologistEntity(
        id = id,
        avatar = avatar,
        name = name,
        surname = surname,
        patronymic = patronymic,
        profile = profile,
        country = country,
        city = city,
        geoPoint = geoPoint,
        contacts = resultContacts,
        education = resultEducation,
        specialization = resultSpecial,
        experience = experience.toInt(),
        rating = rating.toFloat(),
        numberOfVotes = numberOfVotes.toInt()
    )
}

private fun convertSpecial(specialization: Map<String, Any>): Specialization {
    val profession = specialization[KEY_SPECIALIZATION_PROFESSION] as String
    val specializations = specialization[KEY_SPECIALIZATION_SPECIALIZATION] as List<String>
    return Specialization(profession = profession, specialization = specializations)
}

private fun convertEducation(education: List<Map<String, Any>>): List<Education> {
    val result: MutableList<Education> = mutableListOf()
    education.forEach { item ->
        val faculty = item[KEY_EDUCATIONS_FACULTY] as String
        val specialization = item[KEY_EDUCATIONS_SPECIALIZATION] as String
        val university = item[KEY_EDUCATIONS_UNIVERSITY] as String
        val yearOfGraduation = item[KEY_EDUCATIONS_YEAR_OF_GRADUATION] as Number
        val educ = Education(
            faculty = faculty,
            specialization = specialization,
            university = university,
            yearOfGraduation = yearOfGraduation.toInt()
        )
        result.add(educ)
    }
    return result
}

private fun convertContacts(contacts: List<Map<String, Any>>): List<Contact> {
    val result: MutableList<Contact> = mutableListOf()
    contacts.forEach { item ->
        val title = item[KEY_CONTACTS_TITLE_CONTACT] as String
        val contact = item[KEY_CONTACTS_CONTACT] as String
        if (!title.isNullOrEmpty() && !contact.isNullOrEmpty()) {
            val itemContact = Contact(titleContact = title, contact = contact)
            result.add(itemContact)
        }
    }
    return result
}