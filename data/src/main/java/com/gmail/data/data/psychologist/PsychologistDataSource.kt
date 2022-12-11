@file:Suppress("UNREACHABLE_CODE", "UNCHECKED_CAST")

package com.gmail.data.data.psychologist

import com.gmail.data.entity.PsychologistEntity
import com.gmail.data.repository.psychologist.PsychologistRepositoryInterface
import com.gmail.data.utils.convertToPsychologistEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PsychologistDataSource(private val repository: PsychologistRepositoryInterface) :
    PsychologistDataSourceInterface {
    companion object {

    }

    override suspend fun getAllPsychologistEntity(): Flow<List<PsychologistEntity>> {
        return repository.getAllData().map { list ->
            list.map { item ->
                convertToPsychologistEntity(item)
            }
        }
    }

    override suspend fun insertItemPsychologistEntity() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItemPsychologistEntity() {
        TODO("Not yet implemented")
    }
}