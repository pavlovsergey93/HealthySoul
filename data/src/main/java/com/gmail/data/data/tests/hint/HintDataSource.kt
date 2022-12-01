package com.gmail.data.data.tests.hint

import com.gmail.data.entity.tests.questionentity.HintEntity
import com.gmail.data.repository.questionrepository.QuestionsDataSource
import com.gmail.data.repository.questionrepository.QuestionsRepositoryInterface
import io.grpc.InternalChannelz.id
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HintDataSource(private val repository: HintRepositoryInterface) : HintDataSourceInterface {
    companion object {
        private const val KEY_HINT = "hint"
    }

    override suspend fun getHint(hintId: String): Flow<HintEntity> {
        return repository.getHint(hintId).map {
            val hintId = it.id
            val hint = it.data?.get(KEY_HINT) as String
            HintEntity(hintId, hint)
        }
    }
}