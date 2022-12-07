package com.gmai.pavlovsv93.healtysoul.domain.repository.psycholigist

import com.gmail.data.entity.PsychologistEntity
import kotlinx.coroutines.flow.Flow

interface DetailsPsychologistDataSourceInterface {
    suspend fun getDetailsPsychologist(id: String): Flow<PsychologistEntity>
}