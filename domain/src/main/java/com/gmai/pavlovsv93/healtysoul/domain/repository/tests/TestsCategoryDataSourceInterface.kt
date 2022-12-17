package com.gmai.pavlovsv93.healtysoul.domain.repository.tests

import com.gmai.pavlovsv93.healtysoul.domain.models.tests.GeneralTestData
import kotlinx.coroutines.flow.Flow

interface TestsCategoryDataSourceInterface {
    suspend fun getListCategory(): Flow<List<GeneralTestData>>
}