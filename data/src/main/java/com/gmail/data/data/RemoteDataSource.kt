@file:Suppress("UNREACHABLE_CODE", "UNCHECKED_CAST")

package com.gmail.data.data

import com.gmail.data.entity.*
import com.gmail.data.repository.RepositoryInterface
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RemoteDataSource(private val repository: RepositoryInterface) : DataSourceInterface {
    companion object {
        private const val KEY_AVATAR = "avatar"
        private const val KEY_NAME = "name"
        private const val KEY_SURNAME = "surname"
        private const val KEY_PATRONYMIC = "patronymic"
        private const val KEY_PROFILE = "profile"
        private const val KEY_COUNTRY = "country"
        private const val KEY_CITY = "city"
        private const val KEY_GEO_POINT = "geoPoint"
        private const val KEY_CONTACTS_TITLE_CONTACT = "titleContact"
        private const val KEY_CONTACTS_CONTACT = "contact"
        private const val KEY_CONTACTS = "contacts"
        private const val KEY_EDUCATIONS_UNIVERSITY = "university"
        private const val KEY_EDUCATIONS_FACULTY = "faculty"
        private const val KEY_EDUCATIONS_SPECIALIZATION = "specialization"
        private const val KEY_EDUCATIONS_YEAR_OF_GRADUATION = "yearOfGraduation"
        private const val KEY_EDUCATIONS = "education"
        private const val KEY_SPECIALIZATION_PROFESSION = "profession"
        private const val KEY_SPECIALIZATION_SPECIALIZATION = "specialization"
        private const val KEY_SPECIALIZATION = "specialization"
        private const val KEY_EXPERIENCE = "experience"
        private const val KEY_RATING = "rating"
        private const val KEY_NUMBER_OF_VOTES = "numberOfVotes"
    }

    override suspend fun getAllPsychologistEntity(): Flow<List<PsychologistEntity>> {
        return repository.getAllData().map { list ->
            list.map { item ->
                convertToPsychologistEntity(item)
            }
        }
    }

    override suspend fun getItemPsychologistEntity(idPsychologist: String): Flow<PsychologistEntity> {
        return repository.getItemData(idPsychologist)
            .map { psy -> convertToPsychologistEntity(psy) }
    }

    override suspend fun insertItemPsychologistEntity() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItemPsychologistEntity() {
        TODO("Not yet implemented")
    }

    private fun convertToPsychologistEntity(item: DocumentSnapshot): PsychologistEntity {
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
            rating = rating.toDouble(),
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
            if (title != null && contact != null) {
                val itemContact = Contact(titleContact = title, contact = contact)
                result.add(itemContact)
            }
        }
        return result
    }
}