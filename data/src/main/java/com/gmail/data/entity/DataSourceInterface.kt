package com.gmail.data.entity

import kotlinx.coroutines.flow.Flow

interface DataSourceInterface {

    suspend fun getAllPsychologistEntity(): Flow<List<PsychologistEntity>>
    suspend fun getItemPsychologistEntity(idPsychologist: String): Flow<PsychologistEntity>
    suspend fun insertItemPsychologistEntity()
    suspend fun deleteItemPsychologistEntity()
}


// getAll, getItem, insert/delete-Favorite, intentContact