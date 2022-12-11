package com.gmail.data.data.psychologist

import com.gmail.data.entity.PsychologistEntity
import kotlinx.coroutines.flow.Flow

interface DetailsPsychologistDataSourceInterface {
    suspend fun getDetailsPsychologist(id: String): Flow<PsychologistEntity>
}