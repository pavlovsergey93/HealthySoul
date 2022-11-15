@file:Suppress("UNREACHABLE_CODE", "UNCHECKED_CAST")

package com.gmail.data.data

import com.gmail.data.entity.*
import com.gmail.data.repository.RepositoryInterface
import com.gmail.data.utils.*
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RemoteDataSource(private val repository: RepositoryInterface) : DataSourceInterface {

    override suspend fun getAllPsychologistEntity(): Flow<List<PsychologistEntity>> {
        return repository.getAllData().map { resultList ->
            convert(resultList)
        }
    }

    override suspend fun getItemPsychologistEntity(idPsychologist: String): Flow<PsychologistEntity> {
        return repository.getItemData(idPsychologist)
            .map { convertItem(it) }
    }

    override suspend fun insertItemPsychologistEntity() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItemPsychologistEntity() {
        TODO("Not yet implemented")
    }

    private fun convert(list: List<DocumentSnapshot>): List<PsychologistEntity> {
        val listDevice: MutableList<PsychologistEntity> = mutableListOf()
        list.forEach { item ->
            convertItem(item).let {
                if (item.id != "") {
                    listDevice.add(it)
                }
            }
        }
        return listDevice
    }

    private fun convertItem(item: DocumentSnapshot): PsychologistEntity {
        val id = item.id
        item.data
        val avatar = item.data?.get(KEY_AVATAR)
        val name = item.data?.get(KEY_NAME)
        val surname = item.data?.get(KEY_SURNAME)
        val patronymic = item.data?.get(KEY_PATRONYMIC)
        val profile = item.data?.get(KEY_PROFILE)
        val country = item.data?.get(KEY_COUNTRY)
        val city = item.data?.get(KEY_CITY)
        val geoPoint = item.data?.get(KEY_GEO_POINT)
        val titleContact = item.data?.get(KEY_CONTACTS_TITLE_CONTACT)
        val contact = item.data?.get(KEY_CONTACTS_CONTACT)
        val contacts = item.data?.get(KEY_CONTACTS)
        val university = item.data?.get(KEY_EDUCATIONS_UNIVERSITY)
        val faculty = item.data?.get(KEY_EDUCATIONS_FACULTY)
        val specializationEdu = item.data?.get(KEY_EDUCATIONS_SPECIALIZATION)
        val yearOfGraduation = item.data?.get(KEY_EDUCATIONS_YEAR_OF_GRADUATION)
        val education = item.data?.get(KEY_EDUCATIONS)
        val profession = item.data?.get(KEY_SPECIALIZATION_PROFESSION)
        val specializationSpec = item.data?.get(KEY_SPECIALIZATION_SPECIALIZATION)
        val specialization = item.data?.get(KEY_SPECIALIZATION)
        val experience = item.data?.get(KEY_EXPERIENCE)
        val rating = item.data?.get(KEY_RATING)
        val numberOfVotes = item.data?.get(KEY_NUMBER_OF_VOTES)

        return PsychologistEntity(
            id = id as String,
            avatar = avatar as String,
            name = name as String,
            surname = surname as String,
            patronymic = patronymic as String,
            profile = profile as String,
            country = country as String,
            city = city as String,
            geoPoint = geoPoint as GeoPoint,
            contacts = contacts as List<Contact>,
            education = education as List<Education>,
            specialization = specialization as List<Specialization>,
            experience = experience as Int,
            rating = rating as Double,
            numberOfVotes = numberOfVotes as Int
        )

        Contact(
            titleContact = titleContact as String,
            contact = contact as String
        )

        Education(
            university = university as String,
            faculty = faculty as String,
            specialization = specializationEdu as String,
            yearOfGraduation = yearOfGraduation as Int
        )

        Specialization(
            profession = profession as String,
            specialization = specializationSpec as List<String>
        )
    }
}