@file:Suppress("UNREACHABLE_CODE", "UNCHECKED_CAST")

package com.gmail.data.data

import com.gmail.data.entity.DataSourceInterface
import com.gmail.data.entity.PsychologistEntity
import com.gmail.data.repository.RepositoryInterface
import com.gmail.data.utils.convertToPsychologistEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RemoteDataSource(private val repository: RepositoryInterface) : DataSourceInterface {
    companion object {

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
}