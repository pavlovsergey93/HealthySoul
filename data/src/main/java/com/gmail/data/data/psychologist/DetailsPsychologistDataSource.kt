package com.gmail.data.data.psychologist

import com.gmai.pavlovsv93.healtysoul.domain.repository.psycholigist.DetailsPsychologistDataSourceInterface
import com.gmail.data.entity.PsychologistEntity
import com.gmail.data.repository.RepositoryInterface
import com.gmail.data.utils.convertToPsychologistEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DetailsPsychologistDataSource(private val repository: RepositoryInterface) : DetailsPsychologistDataSourceInterface {
    override suspend fun getDetailsPsychologist(id: String): Flow<PsychologistEntity> {
        return repository.getItemData(id).map { psycholigist ->
            convertToPsychologistEntity(psycholigist)
        }
    }
}