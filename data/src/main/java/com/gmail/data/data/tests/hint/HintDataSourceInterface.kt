package com.gmail.data.data.tests.hint

import com.gmail.data.entity.tests.questionentity.HintEntity
import kotlinx.coroutines.flow.Flow

interface HintDataSourceInterface {
    suspend fun getHint(hintId: String): Flow<HintEntity>
}