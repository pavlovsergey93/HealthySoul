@file:Suppress("UNREACHABLE_CODE", "UNCHECKED_CAST")

package com.gmail.data.data.psychologist

import com.gmail.data.data.room.RoomDao
import com.gmail.data.data.room.RoomEntity
import com.gmail.data.entity.PsychologistEntity
import com.gmail.data.repository.psychologist.PsychologistRepositoryInterface
import com.gmail.data.utils.convertToPsychologistEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PsychologistDataSource(
    private val repository: PsychologistRepositoryInterface,
    private val dao: RoomDao

) : PsychologistDataSourceInterface {
    companion object {

    }

    override suspend fun getAllPsychologistEntity(): Flow<List<PsychologistEntity>> {
        return repository.getAllData().map { list ->
            list.map { item ->
                convertToPsychologistEntity(item)
            }
        }
    }

    override suspend fun insertItemPsychologistEntity(entity: RoomEntity) {
        dao.insert(entity)
    }

    override suspend fun deleteItemPsychologistEntity(entity: RoomEntity) {
        dao.delete(entity)
    }
}