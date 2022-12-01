package com.gmai.pavlovsv93.healtysoul.domain.repository.tests

import com.gmai.pavlovsv93.healtysoul.domain.models.tests.HintEntity
import kotlinx.coroutines.flow.Flow

interface HintDataSourceInterface {
    suspend fun getHint(hintId: String): Flow<HintEntity>
}