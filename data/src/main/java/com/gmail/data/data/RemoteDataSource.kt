@file:Suppress("UNREACHABLE_CODE", "UNCHECKED_CAST")

package com.gmail.data.data

import com.gmail.data.entity.*
import com.gmail.data.repository.RepositoryInterface
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
        private const val KEY_RATING = "specialization"
        private const val KEY_NUMBER_OF_VOTES = "numberOfVotes"
    }

//    override suspend fun getAllPsychologistEntity(): Flow<List<PsychologistEntity>> {
//        return repository.getAllData().map { resultList ->
//            convert(resultList)
//        }
//    }

    override suspend fun getAllPsychologistEntity(): Flow<List<PsychologistEntity>> {
        return repository.getAllData().map { list ->
            list.map { item ->
                val contactResult = mutableListOf<Contact>()
                val educationResult = mutableListOf<Education>()
                val specializationSpecResult = mutableListOf<Specialization>()
                val id = item.id
                val avatar = item.data?.get(KEY_AVATAR) as String
                val name = item.data?.get(KEY_NAME) as String
                val surname = item.data?.get(KEY_SURNAME) as String
                val patronymic = item.data?.get(KEY_PATRONYMIC) as String
                val profile = item.data?.get(KEY_PROFILE) as String
                val country = item.data?.get(KEY_COUNTRY) as String
                val city = item.data?.get(KEY_CITY) as String
                val geoPoint = item.data?.get(KEY_GEO_POINT) as GeoPoint
                val titleContact = item.data?.get(KEY_CONTACTS_TITLE_CONTACT) as String
                val contact = item.data?.get(KEY_CONTACTS_CONTACT) as String
                val contacts = item.data?.get(KEY_CONTACTS) as List<Map<String, String>>
                val university = item.data?.get(KEY_EDUCATIONS_UNIVERSITY) as String
                val faculty = item.data?.get(KEY_EDUCATIONS_FACULTY) as String
                val specializationEdu = item.data?.get(KEY_EDUCATIONS_SPECIALIZATION) as String
                val yearOfGraduation = item.data?.get(KEY_EDUCATIONS_YEAR_OF_GRADUATION) as Int
                val education =
                    item.data?.get(KEY_EDUCATIONS) as List<Map<String, Any>>
                val profession = item.data?.get(KEY_SPECIALIZATION_PROFESSION) as String
                val specializationSpec =
                    item.data?.get(KEY_SPECIALIZATION_SPECIALIZATION) as List<Map<String, Any>>
                val specialization = item.data?.get(KEY_SPECIALIZATION) as List<Specialization>
                val experience = item.data?.get(KEY_EXPERIENCE) as Int
                val rating = item.data?.get(KEY_RATING) as Double
                val numberOfVotes = item.data?.get(KEY_NUMBER_OF_VOTES) as Int

                contacts.forEach { itemSublist ->
                    val titleContact =
                        itemSublist[KEY_CONTACTS_TITLE_CONTACT] as String
                    val contact = itemSublist[KEY_CONTACTS_CONTACT] as String
                    contactResult.add(
                        Contact(
                            titleContact = titleContact,
                            contact = contact
                        )
                    )
                }
                education.forEach { itemSublistTwo ->
                    val university = itemSublistTwo[KEY_EDUCATIONS_UNIVERSITY] as String
                    val faculty = itemSublistTwo[KEY_EDUCATIONS_FACULTY] as String
                    val specializationEdu = itemSublistTwo[KEY_EDUCATIONS_SPECIALIZATION] as String
                    val yearOfGraduation = itemSublistTwo[KEY_EDUCATIONS_YEAR_OF_GRADUATION] as Int
                    educationResult.add(
                        Education(
                            university = university,
                            faculty = faculty,
                            specialization = specializationEdu,
                            yearOfGraduation = yearOfGraduation
                        )
                    )
                }
                specializationSpec.forEach { itemSublistThree ->
                    val profession = itemSublistThree[KEY_SPECIALIZATION_PROFESSION] as String
                    val specialization = itemSublistThree[KEY_SPECIALIZATION_SPECIALIZATION] as List<String>
                    specializationSpecResult.add(
                        Specialization(
                            profession = profession,
                            specialization = specialization
                        )
                    )
                }

                PsychologistEntity(
                    id = id,
                    avatar = avatar,
                    name = name,
                    surname = surname,
                    patronymic = patronymic,
                    profile = profile,
                    country = country,
                    city = city,
                    geoPoint = geoPoint,
                    contacts = contacts,
                    education = education,
                    specialization = specializationSpec,
                    experience = experience,
                    rating = rating,
                    numberOfVotes = numberOfVotes
                )
            }

        }
    }

    override suspend fun getItemPsychologistEntity(idPsychologist: String): Flow<PsychologistEntity> {
        TODO("Not yet implemented")
    }


//    override suspend fun getItemPsychologistEntity(idPsychologist: String): Flow<PsychologistEntity> {
//        return repository.getItemData(idPsychologist)
//            .map { convertItem(it) }
//    }

    override suspend fun insertItemPsychologistEntity() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItemPsychologistEntity() {
        TODO("Not yet implemented")
    }

//    private fun convert(list: List<DocumentSnapshot>): List<PsychologistEntity> {
//        val listDevice: MutableList<PsychologistEntity> = mutableListOf()
//        list.forEach { item ->
//            convertItem(item).let {
//                if (item.id != "") {
//                    listDevice.add(it)
//                }
//            }
//        }
//        return listDevice
//    }

//    private fun convertItem(item: DocumentSnapshot): PsychologistEntity {
//        val id = item.id
//        item.data
//        val avatar = item.data?.get(KEY_AVATAR)
//        val name = item.data?.get(KEY_NAME)
//        val surname = item.data?.get(KEY_SURNAME)
//        val patronymic = item.data?.get(KEY_PATRONYMIC)
//        val profile = item.data?.get(KEY_PROFILE)
//        val country = item.data?.get(KEY_COUNTRY)
//        val city = item.data?.get(KEY_CITY)
//        val geoPoint = item.data?.get(KEY_GEO_POINT)
//        val titleContact = item.data?.get(KEY_CONTACTS_TITLE_CONTACT)
//        val contact = item.data?.get(KEY_CONTACTS_CONTACT)
//        val contacts = item.data?.get(KEY_CONTACTS)
//        val university = item.data?.get(KEY_EDUCATIONS_UNIVERSITY)
//        val faculty = item.data?.get(KEY_EDUCATIONS_FACULTY)
//        val specializationEdu = item.data?.get(KEY_EDUCATIONS_SPECIALIZATION)
//        val yearOfGraduation = item.data?.get(KEY_EDUCATIONS_YEAR_OF_GRADUATION)
//        val education = item.data?.get(KEY_EDUCATIONS)
//        val profession = item.data?.get(KEY_SPECIALIZATION_PROFESSION)
//        val specializationSpec = item.data?.get(KEY_SPECIALIZATION_SPECIALIZATION)
//        val specialization = item.data?.get(KEY_SPECIALIZATION)
//        val experience = item.data?.get(KEY_EXPERIENCE)
//        val rating = item.data?.get(KEY_RATING)
//        val numberOfVotes = item.data?.get(KEY_NUMBER_OF_VOTES)
//
//        return PsychologistEntity(
//            id = id,
//            avatar = avatar as String,
//            name = name as String,
//            surname = surname as String,
//            patronymic = patronymic as String,
//            profile = profile as String,
//            country = country as String,
//            city = city as String,
//            geoPoint = geoPoint as GeoPoint,
//            contacts = contacts as List<Contact>,
//            education = education as List<Education>,
//            specialization = specialization as List<Specialization>,
//            experience = experience as Int,
//            rating = rating as Double,
//            numberOfVotes = numberOfVotes as Int
//        )
//
//        Contact(
//            titleContact = titleContact as String,
//            contact = contact as String
//        )
//
//        Education(
//            university = university as String,
//            faculty = faculty as String,
//            specialization = specializationEdu as String,
//            yearOfGraduation = yearOfGraduation as Int
//        )
//
//        Specialization(
//            profession = profession as String,
//            specialization = specializationSpec as List<String>
//        )
//    }
}