package com.gmai.pavlovsv93.healtysoul.domain.repository.psycholigist

import com.gmail.data.entity.PsychologistEntity
import kotlinx.coroutines.flow.Flow

interface PsychologistDataSourceInterface {
    suspend fun getAllPsychologistEntity(): Flow<List<PsychologistEntity>>
    suspend fun insertItemPsychologistEntity()
    suspend fun deleteItemPsychologistEntity()
}


// getAll, getItem, insert/delete-Favorite, intentContact